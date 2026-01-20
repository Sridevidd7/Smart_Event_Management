# Docker Deployment Guide - Smart Event Management

## Free Deployment Options

### Option 1: Deploy Locally with Docker (Completely Free)

#### Prerequisites:
- Docker Desktop installed ([Download](https://www.docker.com/products/docker-desktop))
- Docker Hub account (free at https://hub.docker.com)

#### Steps:

1. **Build and Run Locally**
   ```bash
   # Build the Docker image
   docker build -t smart-event-management:latest .
   
   # Run with docker-compose (includes MySQL)
   docker-compose up -d
   ```

2. **Access the Application**
   - Application runs on: `http://localhost:8080`
   - MySQL accessible on: `localhost:3306`

3. **Stop Services**
   ```bash
   docker-compose down
   ```

---

### Option 2: Deploy to Docker Hub (Free Public Repository)

1. **Create Docker Hub Account**
   - Sign up at https://hub.docker.com

2. **Login to Docker**
   ```bash
   docker login
   # Enter your Docker Hub credentials
   ```

3. **Build and Push Image**
   ```bash
   # Replace 'yourusername' with your Docker Hub username
   docker build -t yourusername/smart-event-management:latest .
   
   docker push yourusername/smart-event-management:latest
   ```

4. **Share with Others**
   - Your public image: `https://hub.docker.com/r/yourusername/smart-event-management`
   - Anyone can pull and run: `docker pull yourusername/smart-event-management:latest`

---

### Option 3: Free Azure Container Registry (with GitHub Actions CI/CD)

#### Setup:
1. Create free Azure account at https://azure.microsoft.com/free
2. Use Azure Container Registry (ACR) - free tier available
3. Deploy to Azure Container Instances (free tier: 1 million requests/month)

#### GitHub Actions Workflow
Create `.github/workflows/deploy.yml`:
```yaml
name: Build and Deploy

on:
  push:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Build Docker image
        run: docker build -t smart-event-management:latest .
      
      - name: Push to registry
        run: |
          docker tag smart-event-management:latest ${{ secrets.REGISTRY_LOGIN_SERVER }}/smart-event-management:latest
          docker push ${{ secrets.REGISTRY_LOGIN_SERVER }}/smart-event-management:latest
```

---

## Database Configuration

### Using docker-compose (Recommended)
- Database automatically initialized with `database.sql`
- Credentials:
  - User: `eventuser`
  - Password: `eventpass123`
  - Host: `mysql` (within Docker network)

### Connecting from Local Machine
```
Host: localhost
Port: 3306
Database: event_club_db
User: eventuser
Password: eventpass123
```

---

## Troubleshooting

### Port Already in Use
```bash
# Check what's using port 8080
netstat -ano | findstr :8080

# Or change ports in docker-compose.yml
```

### View Logs
```bash
docker-compose logs -f app
docker-compose logs -f mysql
```

### Rebuild Image
```bash
docker-compose build --no-cache
docker-compose up -d
```

### Database Not Initializing
```bash
# Check database logs
docker-compose logs mysql

# Manually initialize
docker exec event_club_db mysql -u root -prootpassword < database.sql
```

---

## Recommended: Docker Hub Deployment (Easiest & Completely Free)

1. Push to Docker Hub (as shown in Option 2)
2. Anyone can run your app:
   ```bash
   docker run -p 8080:8080 yourusername/smart-event-management:latest
   ```
3. Share the Docker Hub link with friends/team

---

## Next Steps

1. Install Docker Desktop
2. Run `docker-compose up -d`
3. Visit http://localhost:8080
4. Push to Docker Hub for public sharing
