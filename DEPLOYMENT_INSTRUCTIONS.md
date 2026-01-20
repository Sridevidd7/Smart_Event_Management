# Deployment Instructions

## ✅ Application Built Successfully

Your Smart Event Management System has been packaged and is ready to run!

### 📦 What Was Built
- **JAR File**: `target/smartevent-1.0-SNAPSHOT.jar`
- **Build Status**: SUCCESS ✅
- **Java Version**: Java 17
- **All Dependencies**: Included

---

## 🚀 How to Deploy & Run

### Option 1: Quick Start (Windows)
1. Double-click: **`RUN_APPLICATION.bat`**
2. The application will launch automatically

### Option 2: Manual Run (Command Line)
```bash
# From project directory
java -jar target\smartevent-1.0-SNAPSHOT.jar
```

### Option 3: Using Maven
```bash
mvn exec:java
```

---

## 📋 Prerequisites

Before running the application, ensure:

✅ **Java 17+** installed
```bash
java -version  # Should show Java 17 or higher
```

✅ **MySQL 8.x** running on `localhost:3306`
```bash
# Windows: Start MySQL service
net start MySQL80

# Or verify in MySQL Workbench
```

✅ **Database initialized**
```bash
mysql -u root -p < database.sql
```

---

## 🔧 Database Configuration

The application expects:
- **Host**: `localhost`
- **Port**: `3306`
- **Database**: `event_club_db`
- **User**: `root`
- **Password**: `Sridevi@2006`

To modify, edit: `config.properties` or `src/main/java/com/college/eventclub/DBConnection.java`

---

## 🔐 Default Credentials

After database setup, use these to login:

**Admin Account:**
- Email: `admin@college.edu`
- Password: `admin123`

**Student Account:**
- Email: `student@college.edu`
- Password: `student123`

---

## 🐳 Docker Deployment (Optional)

If Docker is installed:
```bash
# Build Docker image
docker build -t smart-event-management:latest .

# Run with MySQL
docker-compose up -d

# Access on http://localhost:8080
```

**Note**: Docker compose credentials have been updated to match your application.

---

## 📊 Application Features

✅ User Authentication (Admin & Student roles)  
✅ Club Management  
✅ Event Management  
✅ Student Registration  
✅ QR Code Generation & Check-in  
✅ Email Notifications  
✅ Attendance Tracking  

---

## ✨ What's Done

1. ✅ Fixed database credentials mismatch
2. ✅ Built JAR package successfully  
3. ✅ Created quick-start batch script
4. ✅ Updated docker-compose for consistency

---

## 🐛 Troubleshooting

**Issue**: Application won't connect to database
- **Solution**: Verify MySQL is running and credentials match config.properties

**Issue**: "Port 3306 already in use"
- **Solution**: MySQL is already running, which is good. Just make sure database is initialized.

**Issue**: "Cannot find JAR file"
- **Solution**: Run `mvn clean package` first

---

## 📞 Need Help?

Refer to these documents:
- `README.md` - Project overview
- `QUICKSTART.md` - Quick setup guide
- `TESTING_GUIDE.md` - Test the application
- `DOCKER_DEPLOYMENT.md` - Docker deployment options

---

**Deployment Status**: ✅ READY TO DEPLOY
