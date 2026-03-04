from flask import Flask, render_template, request
import joblib
import re
import json
import os
import cv2
import numpy as np
import warnings

# Suppress sklearn version warnings
warnings.filterwarnings('ignore', category=UserWarning)

# Set environment variable before importing TensorFlow
os.environ['TF_ENABLE_ONEDNN_OPTS'] = '0'
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'

import tensorflow as tf
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.sequence import pad_sequences

app = Flask(__name__)

# ==============================
# LOAD MODELS
# ==============================

# Load with custom objects to handle version mismatch
custom_objects = {}
try:
    email_model = load_model("backend/models/email_sms_model.h5", custom_objects=custom_objects, compile=False)
    email_model.compile(loss='binary_crossentropy', optimizer='adam', metrics=['accuracy'])
except Exception as e:
    print(f"Error loading model: {e}")
    print("Please retrain the model with the current TensorFlow/Keras version")
    raise

tokenizer = joblib.load("backend/models/tokenizer.pkl")
encoder_email = joblib.load("backend/models/label_encoder_email.pkl")

url_model = joblib.load("backend/models/url_model.pkl")
url_vectorizer = joblib.load("backend/models/url_vectorizer.pkl")
le_url = joblib.load("backend/models/url_label_encoder.pkl")

max_len = 200
HISTORY_FILE = "history.json"

# ==============================
# HISTORY FUNCTIONS
# ==============================

def load_history():
    if not os.path.exists(HISTORY_FILE):
        return []
    with open(HISTORY_FILE, "r") as f:
        return json.load(f)

def save_history(history):
    with open(HISTORY_FILE, "w") as f:
        json.dump(history, f, indent=4)

# ==============================
# URL CLEANING
# ==============================

def clean_url(url):
    url = url.lower()
    url = re.sub(r'https?://', '', url)
    url = re.sub(r'www\.', '', url)
    return url

# ==============================
# QR DECODER (OpenCV)
# ==============================

def decode_qr_opencv(image_path):
    detector = cv2.QRCodeDetector()
    img = cv2.imread(image_path)

    if img is None:
        return None

    data, bbox, _ = detector.detectAndDecode(img)

    if bbox is not None and data:
        return data
    return None

# ==============================
# MAIN ROUTE
# ==============================

@app.route("/", methods=["GET", "POST"])
def home():

    history = load_history()
    prediction = None
    decoded_value = None

    if request.method == "POST":

        input_type = request.form.get("type")
        content = request.form.get("content")

        # EMAIL / SMS
        if input_type in ["email", "sms"] and content:

            seq = tokenizer.texts_to_sequences([content])
            padded = pad_sequences(seq, maxlen=max_len)

            pred = (email_model.predict(padded) > 0.5).astype("int32")
            prediction = encoder_email.inverse_transform(pred)[0]

        # URL
        elif input_type == "url" and content:

            cleaned = clean_url(content)
            vec = url_vectorizer.transform([cleaned])
            pred = url_model.predict(vec)
            prediction = le_url.inverse_transform(pred)[0]

        # QR CODE
        elif input_type == "qr":

            file = request.files.get("qr_image")

            if file and file.filename != "":
                temp_path = "temp_qr.png"
                file.save(temp_path)

                decoded_text = decode_qr_opencv(temp_path)
                decoded_value = decoded_text

                if decoded_text:

                    if "http" in decoded_text or "www" in decoded_text:

                        cleaned = clean_url(decoded_text)
                        vec = url_vectorizer.transform([cleaned])
                        pred = url_model.predict(vec)
                        prediction = le_url.inverse_transform(pred)[0]

                    else:
                        seq = tokenizer.texts_to_sequences([decoded_text])
                        padded = pad_sequences(seq, maxlen=max_len)
                        pred = (email_model.predict(padded) > 0.5).astype("int32")
                        prediction = encoder_email.inverse_transform(pred)[0]

                else:
                    prediction = "Invalid QR"

                if os.path.exists(temp_path):
                    os.remove(temp_path)

        # SAVE HISTORY
        if prediction:
            history.insert(0, {
                "type": input_type,
                "content": content if content else decoded_value,
                "result": prediction
            })

            history = history[:50]
            save_history(history)

    return render_template(
        "index.html",
        prediction=prediction,
        history=history,
        decoded=decoded_value
    )


if __name__ == "__main__":
    # Download NLTK data if not present
    import nltk
    try:
        nltk.data.find('corpora/stopwords')
    except LookupError:
        nltk.download('stopwords', quiet=True)
    try:
        nltk.data.find('tokenizers/punkt')
    except LookupError:
        nltk.download('punkt', quiet=True)
    try:
        nltk.data.find('corpora/wordnet')
    except LookupError:
        nltk.download('wordnet', quiet=True)
    
    app.run(debug=True)