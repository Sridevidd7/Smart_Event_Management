# Configuration Guide - Smart Event Management System

## Database Configuration

### MySQL Connection Settings

**File**: `src/main/java/com/college/eventclub/DBConnection.java`

**Default Configuration:**
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/event_club_db";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "root";
```

### Customize Connection

**For Different Port:**
```java
private static final String DB_URL = "jdbc:mysql://localhost:3307/event_club_db";
```

**For Different Host:**
```java
private static final String DB_URL = "jdbc:mysql://192.168.1.100:3306/event_club_db";
```

**For Different Database Name:**
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/my_event_db";
```

**For SSL Connection:**
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/event_club_db?useSSL=true&serverTimezone=UTC";
```

---

## Email Configuration

### Email Notification Settings

**File**: `src/main/java/com/college/eventclub/EmailUtil.java`

**Default Configuration:**
```java
private static final String SENDER_EMAIL = "your-email@gmail.com";
private static final String SENDER_PASSWORD = "your-app-password";
private static final String SMTP_HOST = "smtp.gmail.com";
private static final String SMTP_PORT = "587";
```

### Configure for Different Email Providers

**For Gmail:**
```java
private static final String SENDER_EMAIL = "your-email@gmail.com";
private static final String SENDER_PASSWORD = "app-specific-password"; // Use App Password, not Gmail password
private static final String SMTP_HOST = "smtp.gmail.com";
private static final String SMTP_PORT = "587";
```

**For Outlook/Office 365:**
```java
private static final String SENDER_EMAIL = "your-email@outlook.com";
private static final String SENDER_PASSWORD = "your-password";
private static final String SMTP_HOST = "smtp-mail.outlook.com";
private static final String SMTP_PORT = "587";
```

**For Custom SMTP:**
```java
private static final String SENDER_EMAIL = "support@yourdomain.com";
private static final String SENDER_PASSWORD = "your-password";
private static final String SMTP_HOST = "mail.yourdomain.com";
private static final String SMTP_PORT = "587"; // or 25, 465 depending on provider
```

### Gmail Setup for Email Notifications

1. Enable 2-Factor Authentication on Gmail
2. Create App Password:
   - Go to Google Account Security
   - Select "App passwords"
   - Choose "Mail" and "Windows Computer"
   - Copy the generated 16-character password
3. Use this password in `SENDER_PASSWORD` field

---

## Application Configuration

### Application Properties

**Build Configuration**: `pom.xml`

**Change Application Version:**
```xml
<version>1.0-SNAPSHOT</version>
```

**Change Java Version Target:**
```xml
<properties>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
</properties>
```

---

## JVM Configuration

### Increasing Memory for Large Datasets

**Run with More Memory:**
```bash
mvn -Xmx1024m exec:java
```

**Set Environment Variable:**
```bash
# Windows
set MAVEN_OPTS=-Xmx1024m
mvn exec:java

# Linux/Mac
export MAVEN_OPTS=-Xmx1024m
mvn exec:java
```

---

## Database Connection Pooling

### Advanced Configuration

To add connection pooling (optional, for production):

1. Add HikariCP dependency to `pom.xml`:
```xml
<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    <version>5.0.1</version>
</dependency>
```

2. Create `HikariDataSource` in `DBConnection.java`:
```java
private static HikariDataSource dataSource;

static {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(DB_URL);
    config.setUsername(DB_USER);
    config.setPassword(DB_PASSWORD);
    config.setMaximumPoolSize(10);
    dataSource = new HikariDataSource(config);
}

public static Connection getConnection() throws SQLException {
    return dataSource.getConnection();
}
```

---

## Logging Configuration

### Adding Logging (Optional)

**Add SLF4J dependency to `pom.xml`:**
```xml
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>2.0.9</version>
</dependency>
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.4.11</version>
</dependency>
```

**Create `src/main/resources/logback.xml`:**
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <file>logs/smartevent.log</file>
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="INFO">
        <appender-ref ref="FILE" />
    </root>
</configuration>
```

---

## QR Code Configuration

### Customize QR Code Size

**File**: `src/main/java/com/college/eventclub/QRCodeUtil.java`

**Change QR Code Dimensions:**
```java
private static final int QR_WIDTH = 300;   // Default: 300
private static final int QR_HEIGHT = 300;  // Default: 300
```

**For Smaller QR Codes:**
```java
private static final int QR_WIDTH = 200;
private static final int QR_HEIGHT = 200;
```

**For Larger QR Codes:**
```java
private static final int QR_WIDTH = 500;
private static final int QR_HEIGHT = 500;
```

### QR Code Storage Location

**Change Save Directory:**
```java
String filePath = "qrcodes/" + fileName + ".png";
// Change to:
String filePath = "C:/custom/path/" + fileName + ".png";
```

---

## UI Customization

### Change Application Title

**In LoginFrame:**
```java
setTitle("Your Custom Title - Login");
```

### Change Color Scheme

**Primary Color (Blue):**
```java
new Color(0, 102, 204)  // Change RGB values
```

**Background Color:**
```java
new Color(240, 240, 240)  // Change RGB values
```

**Example - Dark Theme:**
```java
mainPanel.setBackground(new Color(30, 30, 30));
titleLabel.setForeground(new Color(255, 200, 0));
```

### Change Font

**In any frame:**
```java
titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
// Change to:
titleLabel.setFont(new Font("Verdana", Font.BOLD, 20));
```

---

## User Management

### Adding New Default Users

**Edit `database.sql` to add users:**
```sql
INSERT INTO users (email, password, name, role) 
VALUES ('newemail@college.edu', 'password123', 'User Name', 'STUDENT');
```

### Change Default Credentials in Login Form

**File**: `LoginFrame.java`

**Pre-filled Login:**
```java
emailField.setText("admin@college.edu");
passwordField.setText("admin123");
```

---

## Database Backup & Restore

### Backup Database

```bash
# Windows
mysqldump -u root -p event_club_db > backup.sql

# Linux/Mac
mysqldump -u root -p event_club_db > backup.sql
```

### Restore Database

```bash
mysql -u root -p event_club_db < backup.sql
```

### Schedule Automatic Backups (Windows)

**Create `backup.bat`:**
```batch
@echo off
set TIMESTAMP=%date:~-4%%date:~-10,2%%date:~-7,2%_%time:~0,2%%time:~3,2%%time:~6,2%
mysqldump -u root -p event_club_db > backup_%TIMESTAMP%.sql
echo Backup completed at %TIMESTAMP%
```

Schedule with Windows Task Scheduler.

---

## Performance Tuning

### Database Optimization

**Add indexes for frequently searched columns:**
```sql
CREATE INDEX idx_registration_user ON registrations(user_id);
CREATE INDEX idx_registration_event ON registrations(event_id);
```

### Query Optimization

**In DAOs, use prepared statements** (already implemented):
```java
PreparedStatement pstmt = conn.prepareStatement(sql);
```

### Caching (Optional)

**For frequently accessed data:**
```java
private static Map<Integer, Club> clubCache = new HashMap<>();

public Club getClubById(int clubId) {
    if (clubCache.containsKey(clubId)) {
        return clubCache.get(clubId);
    }
    Club club = fetchFromDatabase(clubId);
    clubCache.put(clubId, club);
    return club;
}
```

---

## Deployment Configuration

### Production Settings

**Database Configuration:**
- Use strong password for MySQL user
- Create dedicated database user with limited privileges
- Enable SSL for connections

**Email Configuration:**
- Use app-specific passwords for email accounts
- Enable encryption for email communications
- Set up email rate limiting

**JVM Settings:**
```bash
java -Xmx512m -Xms256m -jar smartevent.jar
```

### Network Configuration

**For Remote Database:**
```java
private static final String DB_URL = "jdbc:mysql://production-db.yourdomain.com:3306/event_club_db";
```

**Enable Firewall Rules:**
- Allow port 3306 (MySQL) from application server only
- Allow port 3389 (RDP) from limited IP ranges

---

## Troubleshooting Configuration Issues

### Issue: "Access Denied" for MySQL
**Solution:**
- Verify username and password in `DBConnection.java`
- Check MySQL user permissions:
```sql
GRANT ALL PRIVILEGES ON event_club_db.* TO 'root'@'localhost' IDENTIFIED BY 'root';
FLUSH PRIVILEGES;
```

### Issue: Connection Timeout
**Solution:**
- Increase connection timeout in URL:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/event_club_db?autoReconnect=true&connectTimeout=10000";
```

### Issue: Email Not Sending
**Solution:**
- Check SMTP credentials
- Enable "Less secure app access" for Gmail
- Check firewall rules for SMTP port

---

## Summary Configuration Checklist

- [ ] Database URL configured
- [ ] Database credentials set
- [ ] MySQL service running
- [ ] Email settings configured (optional)
- [ ] JVM memory allocated
- [ ] Font and colors customized (optional)
- [ ] Backup strategy implemented
- [ ] Logs configured (optional)
- [ ] Performance indexes added (optional)

---

**For Additional Help:**
- Check ERROR messages in application console
- Review database.sql for schema details
- Consult Maven documentation for build issues
- Check MySQL documentation for database issues

---

Last Updated: December 2025
Version: 1.0
