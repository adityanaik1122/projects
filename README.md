# AI Spam & Phishing Detector

A modern web application that uses machine learning to detect spam emails, phishing URLs, and malicious QR codes.

## Features

- **Email/SMS Detection**: Classifies text content as spam or ham (legitimate)
- **URL Detection**: Identifies phishing and malicious URLs
- **QR Code Analysis**: Decodes QR codes and analyzes their content
- **Modern UI**: Clean, responsive interface with real-time feedback
- **History Tracking**: Keeps track of previous scans with statistics
- **Real-time Analysis**: Instant results with detailed explanations

## Project Structure

```
project/
├── app.py                    # Main Flask application
├── requirements.txt          # Python dependencies
├── README.md                # This file
├── history.json             # Scan history storage
├── static/
│   ├── style.css           # Modern CSS styles
│   └── script.js           # Interactive JavaScript
├── templates/
│   └── index.html          # Main HTML template
└── backend/
    ├── models/             # Trained ML models
    │   ├── email_sms_model.h5
    │   ├── tokenizer.pkl
    │   ├── label_encoder_email.pkl
    │   ├── url_model.pkl
    │   ├── url_vectorizer.pkl
    │   └── url_label_encoder.pkl
    ├── retrain_email_model.py  # Model retraining script
    ├── merged_email_sms_spam_dataset.csv
    └── merged_url_dataset.csv
```

## Installation

1. **Clone the repository** (if applicable)
2. **Install Python dependencies**:
   ```bash
   pip install -r requirements.txt
   ```

3. **Install NLTK data** (first time only):
   ```python
   import nltk
   nltk.download('stopwords')
   nltk.download('punkt')
   nltk.download('wordnet')
   ```

## Usage

1. **Start the Flask application**:
   ```bash
   python app.py
   ```

2. **Open your browser** and navigate to:
   ```
   http://localhost:5000
   ```

3. **Use the application**:
   - Select content type (Email, SMS, URL, or QR Code)
   - Paste content or upload QR code image
   - Click "Analyze Content" to get results

## Model Retraining

To retrain the email/SMS detection model:

```bash
cd backend
python retrain_email_model.py
```

This will:
- Load the dataset from `merged_email_sms_spam_dataset.csv`
- Preprocess and clean the text data
- Train a new LSTM model
- Save the updated model to `backend/models/`

## Technologies Used

- **Backend**: Flask, TensorFlow, scikit-learn, OpenCV
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)
- **ML Models**: LSTM for text classification, TF-IDF for URL detection
- **Styling**: Modern CSS with gradients, animations, and responsive design
- **Icons**: Font Awesome 6

## Features in Detail

### 1. Email/SMS Detection
- Uses LSTM neural network for text classification
- Preprocessing includes lemmatization and stopword removal
- Binary classification: Spam vs Ham

### 2. URL Detection
- Uses TF-IDF vectorization with scikit-learn
- Classifies URLs as legitimate or phishing
- URL cleaning and normalization

### 3. QR Code Analysis
- OpenCV-based QR code decoding
- Automatic content analysis (URLs vs text)
- Image upload with drag-and-drop support

### 4. User Interface
- Responsive design for all screen sizes
- Real-time character counting
- File upload with preview
- Interactive type selector
- Animated history items
- Statistics dashboard

## API Endpoints

- `GET /` - Main page with form
- `POST /` - Submit content for analysis

## Dependencies

See `requirements.txt` for complete list. Key dependencies:
- Flask: Web framework
- TensorFlow: Deep learning
- scikit-learn: Machine learning
- OpenCV: Image processing
- NLTK: Natural language processing

## Troubleshooting

### TensorFlow DLL Error
If you encounter TensorFlow DLL errors on Windows:
1. Install Microsoft Visual C++ Redistributables (2015-2022)
2. Use TensorFlow 2.10.0 (compatible with Windows)
3. Or use Python 3.9 instead of 3.10

### Model Loading Issues
If models fail to load:
1. Retrain the models using the retraining script
2. Ensure all model files are in `backend/models/`
3. Check TensorFlow/Keras version compatibility

## License

This project is for educational purposes. Feel free to modify and use as needed.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## Acknowledgments

- Dataset sources for spam/legitimate content
- Open source libraries and frameworks
- AI/ML community for resources and tutorials