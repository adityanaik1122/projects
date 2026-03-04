#!/usr/bin/env python3
"""
Simple test script to verify the application structure and imports.
"""

import os
import sys

def check_structure():
    """Check if all required files and directories exist."""
    print("Checking project structure...")
    
    required_files = [
        'app.py',
        'requirements.txt',
        'README.md',
        'templates/index.html',
        'static/style.css',
        'static/script.js',
        'backend/models/email_sms_model.h5',
        'backend/models/tokenizer.pkl',
        'backend/models/label_encoder_email.pkl',
        'backend/models/url_model.pkl',
        'backend/models/url_vectorizer.pkl',
        'backend/models/url_label_encoder.pkl',
        'backend/retrain_email_model.py'
    ]
    
    all_good = True
    for file_path in required_files:
        if os.path.exists(file_path):
            print(f"✓ {file_path}")
        else:
            print(f"✗ {file_path} - MISSING")
            all_good = False
    
    return all_good

def check_imports():
    """Check if Python imports work."""
    print("\nChecking Python imports...")
    
    try:
        import flask
        print("✓ Flask")
    except ImportError:
        print("✗ Flask - Not installed")
        return False
    
    try:
        import tensorflow
        print("✓ TensorFlow")
    except ImportError:
        print("✗ TensorFlow - Not installed")
        return False
    
    try:
        import sklearn
        print("✓ scikit-learn")
    except ImportError:
        print("✗ scikit-learn - Not installed")
        return False
    
    try:
        import cv2
        print("✓ OpenCV")
    except ImportError:
        print("✗ OpenCV - Not installed")
        return False
    
    return True

def main():
    print("=" * 50)
    print("AI Spam & Phishing Detector - System Check")
    print("=" * 50)
    
    structure_ok = check_structure()
    imports_ok = check_imports()
    
    print("\n" + "=" * 50)
    if structure_ok and imports_ok:
        print("✓ All checks passed! The application is ready to run.")
        print("\nTo start the application:")
        print("1. python app.py")
        print("2. Open http://localhost:5000 in your browser")
    else:
        print("✗ Some checks failed. Please fix the issues above.")
        print("\nTo install dependencies:")
        print("pip install -r requirements.txt")
    print("=" * 50)

if __name__ == "__main__":
    main()