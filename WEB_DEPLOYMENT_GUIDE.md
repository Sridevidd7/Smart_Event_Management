# Smart Event Management System - Web Deployment Guide

## ✅ Web Application Successfully Built!

Your application has been converted to a **Spring Boot web application** accessible via HTTP browser!

---

## 🚀 Quick Start

### Option 1: Windows Batch Script (Recommended)
Double-click: **`START_WEB_APP.bat`**

### Option 2: Command Line
```bash
java -jar target\smartevent-1.0-SNAPSHOT.jar
```

### Option 3: Maven
```bash
mvn spring-boot:run
```

---

## 🌐 Access the Application

Once started, open your browser and visit:
```
http://localhost:8080
```

---

## 📋 Prerequisites

Before running, ensure:

✅ **Java 17+** installed
```bash
java -version  # Should show Java 17 or higher
```

✅ **MySQL 8.x** running on `localhost:3306`

✅ **Database initialized**
```bash
mysql -u root -p < database.sql
# Password: Sridevi@2006
```

---

## 📚 What's New

### Web Interface Features:
- ✅ Modern, responsive web UI
- ✅ Login & Registration
- ✅ View upcoming events
- ✅ Register for events
- ✅ QR code generation for check-in
- ✅ Real-time event updates

### REST API Endpoints:
```
POST   /api/auth/login          - User login
POST   /api/auth/register       - User registration
GET    /api/events              - List all events
GET    /api/events/{id}         - Get event details
POST   /api/events              - Create new event (Admin)
PUT    /api/events/{id}         - Update event (Admin)
DELETE /api/events/{id}         - Delete event (Admin)
```

---

## 🔐 Default Test Credentials

**Admin Account:**
- Email: `admin@college.edu`
- Password: `admin123`

**Student Account:**
- Email: `student@college.edu`
- Password: `student123`

---

## ☁️ Cloud Deployment Options

Once you have the web app working locally, you can deploy to:

### Option 1: Docker (Local)
```bash
docker build -t smart-event-web:latest .
docker run -p 8080:8080 smart-event-web:latest
```

### Option 2: Azure App Service
```bash
az webapp up --name <your-app-name> --resource-group <your-rg> --runtime "JAVA|17-java17"
```

### Option 3: AWS Elastic Beanstalk
```bash
eb init
eb create smart-event-env
eb deploy
```

### Option 4: Heroku
```bash
heroku create <app-name>
git push heroku main
```

### Option 5: DigitalOcean App Platform
- Create droplet
- Upload JAR file
- Run: `java -jar smartevent-1.0-SNAPSHOT.jar`

---

## 🔧 Configuration

Edit `src/main/resources/application.properties` to customize:

```properties
# Server
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/event_club_db
spring.datasource.username=root
spring.datasource.password=Sridevi@2006

# Email (Optional)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-password
```

---

## 📊 Architecture

```
Smart Event Management System (Web Version)
│
├── Frontend (HTML/CSS/JavaScript)
│   └── Modern, responsive UI running in browser
│
├── Backend (Spring Boot REST API)
│   ├── Authentication Service
│   ├── Event Management Service
│   ├── Registration Service
│   └── Attendance Service
│
└── Database (MySQL)
    └── All data persistence
```

---

## 🧪 Testing

Test the application:

1. **Register**: Create a new student account
2. **Login**: Log in with created credentials
3. **View Events**: See available events
4. **Register for Event**: Click "Register" button
5. **View QR Code**: Generate QR for check-in

---

## 🐛 Troubleshooting

### App won't start
- Check Java version: `java -version`
- Verify MySQL is running
- Check database credentials in `application.properties`

### Can't connect to database
- Ensure MySQL is running
- Verify credentials match
- Run: `mysql -u root -p` to test connection

### Port 8080 already in use
- Change port in `application.properties`: `server.port=8081`
- Or kill process: `lsof -ti:8080 | xargs kill -9`

### Database not initialized
- Run: `mysql -u root -p < database.sql`
- Enter password: `Sridevi@2006`

---

## 📈 Performance Tips

1. **Increase Memory** (if slow):
   ```bash
   java -Xmx512m -Xms256m -jar smartevent-1.0-SNAPSHOT.jar
   ```

2. **Enable Compression**:
   Add to `application.properties`:
   ```properties
   server.compression.enabled=true
   server.compression.min-response-size=1024
   ```

3. **Use Connection Pooling**:
   Already configured with HikariCP

---

## 🌍 Sharing Your Link

Once deployed to cloud, you can share:
```
https://your-app-domain.com
```

Share this single link with anyone to use the application!

---

## 📞 Support

For more information:
- Check `README.md` for project overview
- See `DOCKER_DEPLOYMENT.md` for Docker options
- Review `TESTING_GUIDE.md` for test procedures

---

**Status**: ✅ Web Application Ready to Deploy

**Next Steps**:
1. ✅ Run the application locally to test
2. ✅ Share the link once deployed to cloud
3. ✅ Share database credentials with team (securely)
4. ✅ Monitor application logs for issues

Happy deploying! 🚀
