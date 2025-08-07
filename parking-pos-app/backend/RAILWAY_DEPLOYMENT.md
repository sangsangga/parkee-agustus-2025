# Railway Deployment Guide for Parking POS System

This guide will help you deploy your parking POS microservices to Railway.

## 🚀 Prerequisites

1. **GitHub Repository**: Push your code to GitHub
2. **Railway Account**: Sign up at [railway.app](https://railway.app)
3. **PostgreSQL Database**: Will be created on Railway

## 📋 Deployment Steps

### Step 1: Create Railway Project

1. Go to [railway.app](https://railway.app) and sign in
2. Click "New Project"
3. Select "Deploy from GitHub repo"
4. Connect your GitHub account and select your repository

### Step 2: Create PostgreSQL Database

1. In your Railway project dashboard, click "New Service"
2. Select "Database" → "PostgreSQL"
3. Railway will automatically create a PostgreSQL instance
4. Note: Database URL and credentials are automatically generated

### Step 3: Deploy Services

Deploy each service as a separate Railway service:

#### 3.1 Deploy Ticket Service (Deploy First)
1. Click "New Service" → "GitHub Repo"
2. Select your repository
3. Set **Root Directory**: `backend/ticket-service`
4. Service will auto-deploy

#### 3.2 Deploy Checkin Service
1. Click "New Service" → "GitHub Repo"
2. Select your repository  
3. Set **Root Directory**: `backend/checkin-service`
4. Service will auto-deploy

#### 3.3 Deploy Checkout Service
1. Click "New Service" → "GitHub Repo"
2. Select your repository
3. Set **Root Directory**: `backend/checkout-service`
4. Service will auto-deploy

### Step 4: Configure Environment Variables

Railway will automatically set some variables, but you need to configure service URLs:

#### For Checkin Service:
```
TICKET_SERVICE_URL=https://ticket-service-production-xxxx.up.railway.app
```

#### For Checkout Service:
```
TICKET_SERVICE_URL=https://ticket-service-production-xxxx.up.railway.app
```

#### For All Services (Optional - Railway auto-configures):
```
DATABASE_URL=postgresql://...
PORT=8080
DDL_AUTO=update
LOG_LEVEL=INFO
```

### Step 5: Set Service URLs

1. Wait for **ticket-service** to deploy first
2. Copy its Railway URL (e.g., `https://ticket-service-production-xxxx.up.railway.app`)
3. Go to **checkin-service** → Variables → Add:
   - `TICKET_SERVICE_URL` = `https://ticket-service-production-xxxx.up.railway.app`
4. Go to **checkout-service** → Variables → Add:
   - `TICKET_SERVICE_URL` = `https://ticket-service-production-xxxx.up.railway.app`
5. Redeploy checkin and checkout services

## 🔗 Service URLs After Deployment

After deployment, you'll have URLs like:
- **Checkin Service**: `https://checkin-service-production-xxxx.up.railway.app`
- **Ticket Service**: `https://ticket-service-production-xxxx.up.railway.app`
- **Checkout Service**: `https://checkout-service-production-xxxx.up.railway.app`

## 🧪 Testing Your Deployment

### Test Checkin Service:
```bash
curl -X POST https://checkin-service-production-xxxx.up.railway.app/checkin \
  -H "Content-Type: application/json" \
  -d '{"plateNumber": "ABC123"}'
```

### Test Checkout Service:
```bash
curl -X POST https://checkout-service-production-xxxx.up.railway.app/checkout \
  -H "Content-Type: application/json" \
  -d '{"plateNumber": "ABC123"}'
```

### Health Check:
```bash
curl https://ticket-service-production-xxxx.up.railway.app/actuator/health
```

## 🛠️ Environment Variables Reference

| Variable | Description | Default |
|----------|-------------|---------|
| `PORT` | Service port | Railway auto-assigns |
| `DATABASE_URL` | PostgreSQL connection | Railway auto-generates |
| `TICKET_SERVICE_URL` | Ticket service URL | Must be set manually |
| `DDL_AUTO` | Hibernate DDL mode | `update` |
| `LOG_LEVEL` | Logging level | `INFO` |

## 🐛 Troubleshooting

### Service Won't Start
- Check logs in Railway dashboard
- Verify environment variables are set
- Ensure database connection is working

### Service Communication Errors
- Verify `TICKET_SERVICE_URL` is correctly set
- Check if ticket-service is running first
- Review network logs

### Database Issues
- Ensure PostgreSQL service is running
- Check `DATABASE_URL` is properly set
- Verify database migrations ran successfully

## 📊 Monitoring

Railway provides built-in monitoring:
- **Logs**: View real-time application logs
- **Metrics**: Monitor CPU, memory, and network usage
- **Health Checks**: Automatic health monitoring via `/actuator/health`

## 💰 Cost Estimation

**Railway Pricing (as of 2024):**
- **Hobby Plan**: $5/month (includes PostgreSQL)
- **Pro Plan**: $20/month (higher limits)
- **Pay-per-use**: Based on resource consumption

**Your Setup Cost:**
- 3 services + 1 database ≈ $5-10/month on Hobby plan

## 🔄 Auto-Deployment

Railway automatically redeploys when you push to your GitHub repository. Each service monitors its respective directory for changes.

## ✅ Deployment Checklist

- [ ] Code pushed to GitHub
- [ ] Railway project created
- [ ] PostgreSQL database added
- [ ] Ticket service deployed first
- [ ] Checkin service deployed with correct `TICKET_SERVICE_URL`
- [ ] Checkout service deployed with correct `TICKET_SERVICE_URL`
- [ ] All services health checks passing
- [ ] API endpoints tested
- [ ] Database tables created successfully

## 🎉 Success!

Your parking POS system is now live on Railway! You can use the service URLs to integrate with your frontend application.
