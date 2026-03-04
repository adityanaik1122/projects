import pandas as pd
import numpy as np
import re
import os
import joblib
import nltk
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize
from nltk.stem import WordNetLemmatizer
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Embedding, LSTM, Dense, Dropout
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.callbacks import EarlyStopping

# Download required NLTK data
try:
    nltk.data.find('corpora/stopwords')
except LookupError:
    nltk.download('stopwords')
try:
    nltk.data.find('tokenizers/punkt')
except LookupError:
    nltk.download('punkt')
try:
    nltk.data.find('corpora/wordnet')
except LookupError:
    nltk.download('wordnet')

print("Loading dataset...")
df = pd.read_csv("merged_email_sms_spam_dataset.csv")[['label','text']]
df.dropna(inplace=True)
df.drop_duplicates(inplace=True)

lemmatizer = WordNetLemmatizer()
stop_words = set(stopwords.words("english"))

def preprocess(text):
    text = text.lower()
    text = re.sub(r'[^a-zA-Z]', ' ', text)
    tokens = word_tokenize(text)
    tokens = [lemmatizer.lemmatize(w) for w in tokens 
              if w not in stop_words and len(w) > 2]
    return " ".join(tokens)

print("Preprocessing text...")
df["clean"] = df["text"].apply(preprocess)

encoder_email = LabelEncoder()
y = encoder_email.fit_transform(df["label"])

X_train_text, X_test_text, y_train, y_test = train_test_split(
    df["clean"], y,
    test_size=0.2,
    random_state=42,
    stratify=y
)

max_words = 10000
max_len = 200

tokenizer = Tokenizer(num_words=max_words)
tokenizer.fit_on_texts(X_train_text)

X_train = pad_sequences(
    tokenizer.texts_to_sequences(X_train_text),
    maxlen=max_len
)

X_test = pad_sequences(
    tokenizer.texts_to_sequences(X_test_text),
    maxlen=max_len
)

print("Building model...")
model_email = Sequential([
    Embedding(max_words, 64),
    LSTM(32),
    Dropout(0.5),
    Dense(16, activation='relu'),
    Dense(1, activation='sigmoid')
])

model_email.compile(loss='binary_crossentropy',
                    optimizer='adam',
                    metrics=['accuracy'])

early_stop = EarlyStopping(monitor='val_loss',
                           patience=2,
                           restore_best_weights=True)

print("Training model...")
model_email.fit(
    X_train, y_train,
    epochs=10,
    batch_size=16,
    validation_split=0.2,
    callbacks=[early_stop],
    verbose=1
)

loss, accuracy = model_email.evaluate(X_test, y_test)
print(f"\nTest Accuracy: {accuracy:.4f}")

os.makedirs("models", exist_ok=True)

print("Saving model...")
model_email.save("models/email_sms_model.h5")
joblib.dump(tokenizer, "models/tokenizer.pkl")
joblib.dump(encoder_email, "models/label_encoder_email.pkl")
print("Model saved successfully!")
