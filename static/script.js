document.addEventListener('DOMContentLoaded', function() {
    // Type selector functionality
    const typeOptions = document.querySelectorAll('.type-option');
    const typeInput = document.getElementById('type-input');
    const qrUpload = document.getElementById('qr-upload');
    const contentTextarea = document.getElementById('content');
    const charCount = document.getElementById('char-count');
    const uploadArea = document.getElementById('upload-area');
    const fileInput = document.getElementById('qr_image');
    const filePreview = document.getElementById('file-preview');
    const detectionForm = document.getElementById('detection-form');

    // Initialize type selector
    typeOptions.forEach(option => {
        option.addEventListener('click', function() {
            const value = this.getAttribute('data-value');
            
            // Update active state
            typeOptions.forEach(opt => opt.classList.remove('active'));
            this.classList.add('active');
            
            // Update hidden input
            typeInput.value = value;
            
            // Show/hide QR upload
            if (value === 'qr') {
                qrUpload.style.display = 'block';
                contentTextarea.placeholder = 'QR code will be analyzed from uploaded image...';
            } else {
                qrUpload.style.display = 'none';
                contentTextarea.placeholder = getPlaceholderForType(value);
            }
        });
    });

    // Set initial active type
    typeOptions[0].classList.add('active');

    // Character counter
    contentTextarea.addEventListener('input', function() {
        charCount.textContent = this.value.length;
    });

    // File upload functionality
    uploadArea.addEventListener('click', function() {
        fileInput.click();
    });

    uploadArea.addEventListener('dragover', function(e) {
        e.preventDefault();
        this.style.background = '#f0f0f0';
    });

    uploadArea.addEventListener('dragleave', function(e) {
        e.preventDefault();
        this.style.background = '';
    });

    uploadArea.addEventListener('drop', function(e) {
        e.preventDefault();
        this.style.background = '';
        
        if (e.dataTransfer.files.length) {
            handleFileSelect(e.dataTransfer.files[0]);
        }
    });

    fileInput.addEventListener('change', function(e) {
        if (this.files.length) {
            handleFileSelect(this.files[0]);
        }
    });

    function handleFileSelect(file) {
        if (!file.type.match('image.*')) {
            alert('Please select an image file');
            return;
        }

        // Update file preview
        const reader = new FileReader();
        reader.onload = function(e) {
            filePreview.innerHTML = `
                <div class="file-info">
                    <img src="${e.target.result}" alt="QR Code Preview">
                    <p>${file.name} (${(file.size / 1024).toFixed(1)} KB)</p>
                </div>
            `;
            filePreview.style.display = 'block';
        };
        reader.readAsDataURL(file);
    }

    // Form submission animation
    detectionForm.addEventListener('submit', function(e) {
        const submitBtn = this.querySelector('.btn-submit');
        const originalText = submitBtn.innerHTML;
        
        // Show loading state
        submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Analyzing...';
        submitBtn.disabled = true;
        
        // Re-enable after 3 seconds (in case of error)
        setTimeout(() => {
            submitBtn.innerHTML = originalText;
            submitBtn.disabled = false;
        }, 3000);
    });

    // Add some sample history items if empty
    if (document.querySelector('.empty-history')) {
        setTimeout(() => {
            const historyList = document.querySelector('.history-list');
            if (historyList && historyList.children.length === 0) {
                // You could add sample data here if needed
            }
        }, 1000);
    }

    // Helper function to get placeholder text
    function getPlaceholderForType(type) {
        const placeholders = {
            'email': 'Paste your email content here...\nExample: "Congratulations! You\'ve won a $1000 gift card..."',
            'sms': 'Paste your SMS message here...\nExample: "URGENT: Your account has been locked. Click here to verify..."',
            'url': 'Enter the URL to check...\nExample: "http://secure-bank-login.com/verify"'
        };
        return placeholders[type] || 'Enter content to analyze...';
    }

    // Update placeholder based on initial type
    contentTextarea.placeholder = getPlaceholderForType('email');

    // Add animation to history items
    const historyItems = document.querySelectorAll('.history-item');
    historyItems.forEach((item, index) => {
        item.style.animationDelay = `${index * 0.1}s`;
        item.classList.add('animate-in');
    });

    // Add tooltips
    const tooltips = document.querySelectorAll('[data-tooltip]');
    tooltips.forEach(element => {
        element.addEventListener('mouseenter', function() {
            const tooltip = document.createElement('div');
            tooltip.className = 'tooltip';
            tooltip.textContent = this.getAttribute('data-tooltip');
            document.body.appendChild(tooltip);
            
            const rect = this.getBoundingClientRect();
            tooltip.style.left = `${rect.left + rect.width / 2}px`;
            tooltip.style.top = `${rect.top - 10}px`;
            tooltip.style.transform = 'translateX(-50%) translateY(-100%)';
            
            this.tooltip = tooltip;
        });
        
        element.addEventListener('mouseleave', function() {
            if (this.tooltip) {
                this.tooltip.remove();
                this.tooltip = null;
            }
        });
    });

    // Add keyboard shortcuts
    document.addEventListener('keydown', function(e) {
        // Ctrl+Enter to submit form
        if (e.ctrlKey && e.key === 'Enter') {
            detectionForm.submit();
        }
        
        // Escape to clear form
        if (e.key === 'Escape') {
            contentTextarea.value = '';
            charCount.textContent = '0';
            filePreview.style.display = 'none';
            fileInput.value = '';
        }
    });

    // Add copy to clipboard for decoded QR
    const decodedQr = document.querySelector('.decoded-qr');
    if (decodedQr) {
        const copyBtn = document.createElement('button');
        copyBtn.className = 'copy-btn';
        copyBtn.innerHTML = '<i class="fas fa-copy"></i> Copy';
        copyBtn.addEventListener('click', function() {
            const text = decodedQr.querySelector('p').textContent;
            navigator.clipboard.writeText(text).then(() => {
                this.innerHTML = '<i class="fas fa-check"></i> Copied!';
                setTimeout(() => {
                    this.innerHTML = '<i class="fas fa-copy"></i> Copy';
                }, 2000);
            });
        });
        decodedQr.appendChild(copyBtn);
    }
});

// Add CSS for animations
const style = document.createElement('style');
style.textContent = `
    .animate-in {
        animation: slideIn 0.3s ease-out forwards;
        opacity: 0;
    }
    
    @keyframes slideIn {
        from {
            opacity: 0;
            transform: translateX(-20px);
        }
        to {
            opacity: 1;
            transform: translateX(0);
        }
    }
    
    .tooltip {
        position: fixed;
        background: rgba(0, 0, 0, 0.8);
        color: white;
        padding: 5px 10px;
        border-radius: 4px;
        font-size: 0.85rem;
        z-index: 1000;
        pointer-events: none;
        white-space: nowrap;
    }
    
    .copy-btn {
        background: #667eea;
        color: white;
        border: none;
        padding: 5px 10px;
        border-radius: 4px;
        cursor: pointer;
        font-size: 0.9rem;
        margin-top: 10px;
        display: flex;
        align-items: center;
        gap: 5px;
    }
    
    .copy-btn:hover {
        background: #764ba2;
    }
`;
document.head.appendChild(style);