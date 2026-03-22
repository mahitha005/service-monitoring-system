# 🚀 PRODUCTION DEPLOYMENT - FINAL

## ✅ Backend (Render) Environment Variables:
DATABASE_URL=jdbc:postgresql://ep-frosty-pond-ah6ggxx8-pooler.c-3.us-east-1.aws.neon.tech/neondb?sslmode=require&channelBinding=require
DATABASE_USERNAME=neondb_owner
DATABASE_PASSWORD=npg_rWtygAC18pVv
EMAILJS_SERVICE_ID=service_ic1xfgg
EMAILJS_OTP_TEMPLATE_ID=template_32q3mn5
EMAILJS_ALERT_TEMPLATE_ID=template_pszv2ji
EMAILJS_PUBLIC_KEY=L0Z9NjqQttvsBGlnl
EMAILJS_PRIVATE_KEY=[GET FROM EMAILJS DASHBOARD]

## ✅ Frontend (Vercel) Environment Variable:
VITE_API_URL=https://service-monitoring-system.onrender.com

## 📧 EmailJS Template Variables:
- OTP Template: {{email}} {{otp_code}}
- Alert Template: {{email}} {{subject}} {{message}}

## 🎯 Deploy Steps:
1. Get EMAILJS_PRIVATE_KEY from EmailJS Dashboard → Account → API Keys
2. Add all environment variables to Render
3. Push backend code → Render auto-deploys
4. Push frontend code → Vercel auto-deploys
5. Test: Register → Check email → Verify OTP → Login

READY FOR FINAL DEPLOYMENT! 🚀