# Deployment Guide

## Push to GitHub

1. **Initialize Git** (if not already done):
   ```bash
   git init
   git add .
   git commit -m "Initial commit: AI Spam & Phishing Detector"
   ```

2. **Create GitHub Repository**:
   - Go to https://github.com/new
   - Create a new repository (e.g., "ai-spam-detector")
   - Don't initialize with README (we already have one)

3. **Push to GitHub**:
   ```bash
   git remote add origin https://github.com/YOUR_USERNAME/ai-spam-detector.git
   git branch -M main
   git push -u origin main
   ```

## Hosting Platform Comparison

### ✅ RECOMMENDED: Render (Best for this project)

**Pros:**
- ✅ Supports Python/Flask perfectly
- ✅ Free tier available (750 hours/month)
- ✅ Handles large ML models well
- ✅ Easy deployment from GitHub
- ✅ Supports file uploads
- ✅ Good for TensorFlow/scikit-learn apps
- ✅ Persistent storage options

**Cons:**
- ⚠️ Free tier spins down after 15 min inactivity (cold starts)
- ⚠️ Limited to 512MB RAM on free tier

**Deployment Steps:**
1. Go to https://render.com
2. Sign up with GitHub
3. Click "New +" → "Web Service"
4. Connect your GitHub repository
5. Configure:
   - **Name**: ai-spam-detector
   - **Environment**: Python 3
   - **Build Command**: `pip install -r requirements.txt`
   - **Start Command**: `gunicorn app:app`
   - **Instance Type**: Free
6. Add environment variables (if needed)
7. Click "Create Web Service"

### ❌ NOT RECOMMENDED: Vercel

**Why not Vercel:**
- ❌ Designed for Next.js/Node.js (not Python)
- ❌ Serverless functions have 50MB limit (your models are larger)
- ❌ 10-second execution timeout (ML inference might exceed this)
- ❌ Complex setup for Flask apps
- ❌ Not ideal for ML/AI applications

## Alternative Options

### Railway
- Similar to Render
- Good Python support
- $5/month minimum after trial
- https://railway.app

### Heroku
- Classic choice for Flask apps
- No free tier anymore ($7/month minimum)
- Good ML support
- https://heroku.com

### Google Cloud Run
- Pay-as-you-go
- Good for ML apps
- More complex setup
- Free tier: 2 million requests/month

### Hugging Face Spaces
- Free for ML apps
- Great for AI/ML projects
- Easy deployment
- https://huggingface.co/spaces

## Recommended: Render Deployment

### Step-by-Step:

1. **Prepare your repository** (already done with files above)

2. **Sign up on Render**:
   - Go to https://render.com
   - Sign up with your GitHub account

3. **Create New Web Service**:
   - Click "New +" button
   - Select "Web Service"
   - Connect your GitHub repository

4. **Configure Service**:
   ```
   Name: ai-spam-detector
   Environment: Python 3
   Region: Choose closest to you
   Branch: main
   Build Command: pip install -r requirements.txt
   Start Command: gunicorn app:app
   ```

5. **Advanced Settings** (Optional):
   - Add environment variables if needed
   - Set health check path: `/`

6. **Deploy**:
   - Click "Create Web Service"
   - Wait 5-10 minutes for first deployment
   - Your app will be live at: `https://ai-spam-detector.onrender.com`

### Important Notes for Render:

1. **Cold Starts**: Free tier spins down after 15 min. First request after sleep takes 30-60 seconds.

2. **Model Files**: Your models in `backend/models/` will be included (make sure they're in Git).

3. **NLTK Data**: Add this to your app.py if not already:
   ```python
   import nltk
   nltk.download('stopwords', quiet=True)
   nltk.download('punkt', quiet=True)
   nltk.download('wordnet', quiet=True)
   ```

4. **Memory**: If you hit memory limits, consider:
   - Upgrading to paid tier ($7/month for 2GB RAM)
   - Optimizing model sizes
   - Using model quantization

## Testing Your Deployment

After deployment, test:
1. ✅ Homepage loads
2. ✅ Email/SMS detection works
3. ✅ URL detection works
4. ✅ QR code upload works
5. ✅ History saves correctly
6. ✅ Statistics display properly

## Troubleshooting

### Issue: Models not loading
- Ensure `backend/models/` is in Git (not in .gitignore)
- Check file sizes (Git has 100MB limit per file)

### Issue: Out of memory
- Upgrade to paid tier
- Optimize models
- Use model compression

### Issue: Slow cold starts
- Upgrade to paid tier (no sleep)
- Use Railway or Google Cloud Run
- Implement keep-alive ping

### Issue: File upload fails
- Check Render's file size limits
- Ensure temp directory is writable

## Cost Comparison

| Platform | Free Tier | Paid Tier | Best For |
|----------|-----------|-----------|----------|
| **Render** | 750 hrs/month | $7/month | ML apps, Flask |
| Vercel | Limited | $20/month | Next.js, static |
| Railway | Trial only | $5/month | Any framework |
| Heroku | None | $7/month | Traditional apps |
| Hugging Face | Unlimited | Free | ML demos |

## Final Recommendation

**Use Render** for this project because:
1. Perfect for Flask + ML apps
2. Free tier is generous
3. Easy GitHub integration
4. Handles TensorFlow well
5. Good documentation

If you need 24/7 uptime without cold starts, upgrade to Render's paid tier ($7/month) or use Railway.