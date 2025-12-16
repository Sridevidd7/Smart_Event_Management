# Quick Start Guide - Smart Event Management System

## 🚀 5-Minute Setup

### Prerequisites Check
- [ ] Java 17+ installed (`java -version`)
- [ ] Maven installed (`mvn -version`)
- [ ] MySQL 8.x running (`mysql --version`)

### Step 1: Database Setup (2 minutes)

**Option A: Using MySQL Command Line**
```bash
cd c:\Users\Usha sri\Desktop\smartevent
mysql -u root -p
```
Then run the SQL script:
```sql
source database.sql;
```

**Option B: Using MySQL Workbench**
1. Open MySQL Workbench
2. Create new query
3. Copy-paste contents of `database.sql`
4. Execute (Ctrl+Enter)

### Step 2: Build Project (1 minute)

```bash
cd c:\Users\Usha sri\Desktop\smartevent
mvn clean compile
```

### Step 3: Run Application (1 minute)

```bash
mvn exec:java
# Or double-click run.bat
```

### Step 4: Login (1 minute)

**Admin Login:**
```
Email: admin@college.edu
Password: admin123
```

**Student Login:**
```
Email: student@college.edu
Password: student123
```

---

## 🎯 Quick Test Scenarios

### Admin Workflow
1. Login with admin credentials
2. Click "Manage Clubs" → Add a new club
3. Click "Add Event" → Create an event with the new club
4. Click "View Events" → See the created event

### Student Workflow
1. Login with student credentials
2. Click "View Available Events" → See all events
3. Click "Register for Event" → Get QR token
4. Click "View My QR Codes" → See QR code display

### Attendance Check-In
1. Login as Admin
2. Click "QR Check-In"
3. Enter the QR token from student registration
4. Verify check-in successful

---

## 📁 File Structure

```
smartevent/
├── pom.xml                    ← Maven configuration
├── database.sql               ← Database schema
├── README.md                  ← Full documentation
├── PROJECT_SUMMARY.md         ← What's included
├── run.bat                    ← Windows run script
└── src/main/java/com/college/eventclub/
    ├── DBConnection.java
    ├── LoginFrame.java
    ├── AdminDashboardFrame.java
    ├── StudentDashboardFrame.java
    ├── ManageClubsFrame.java
    ├── AddEventFrame.java
    ├── ViewEventsFrame.java
    ├── ViewRegistrationsFrame.java
    ├── QRCheckInFrame.java
    ├── StudentViewEventsFrame.java
    ├── StudentMyRegistrationsFrame.java
    ├── StudentMyQRCodesFrame.java
    ├── User.java
    ├── Club.java
    ├── Event.java
    ├── Registration.java
    ├── UserDAO.java
    ├── ClubDAO.java
    ├── EventDAO.java
    ├── RegistrationDAO.java
    ├── AttendanceDAO.java
    ├── QRCodeUtil.java
    └── EmailUtil.java
```

---

## ⚙️ Configuration

### Database Connection
Edit `DBConnection.java` if your MySQL setup differs:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/event_club_db";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "root";
```

### Email Configuration
Edit `EmailUtil.java` to enable email notifications:
```java
private static final String SENDER_EMAIL = "your-email@gmail.com";
private static final String SENDER_PASSWORD = "your-app-password";
```

---

## 🔍 Troubleshooting

### "Database connection failed"
- ✓ Start MySQL: `net start MySQL80` (or your version)
- ✓ Check credentials in `DBConnection.java`
- ✓ Verify database exists: `mysql -u root -p -e "SHOW DATABASES;"`

### "Maven not found"
- ✓ Install Maven from apache.org
- ✓ Add to PATH
- ✓ Restart terminal and verify: `mvn -version`

### "Java not found"
- ✓ Install Java 17+
- ✓ Add JAVA_HOME to environment variables
- ✓ Verify: `java -version`

### Compilation errors
- ✓ Run: `mvn clean install`
- ✓ Check internet (needs to download dependencies)

---

## 📞 Support

**Common Issues:**

| Issue | Solution |
|-------|----------|
| Port 3306 in use | Change MySQL port or stop other MySQL instances |
| Out of memory | Increase JVM memory: `mvn -Xmx1024m exec:java` |
| QR code not displaying | Check ZXing library is in classpath |
| No databases shown | Run database.sql first |

---

## 💡 Tips

- **Auto-login**: Credentials are pre-filled in login screen
- **Test data**: Default users included in database.sql
- **Database reset**: Delete and re-run database.sql to reset all data
- **Logs**: Check console output for debugging information

---

## ✅ Verification Checklist

After running, verify these features work:

- [ ] Login successful
- [ ] Dashboard opens
- [ ] Can add a club
- [ ] Can create an event
- [ ] Can view events
- [ ] QR token generated for registration
- [ ] Can check-in with QR token
- [ ] No errors in console

---

## 🎉 You're Ready!

Your Smart Event Management System is now running. 

**Start managing events!**

For detailed information, see:
- README.md - Full documentation
- PROJECT_SUMMARY.md - What's included

---

Last Updated: December 2025
Status: ✅ Ready to Use
