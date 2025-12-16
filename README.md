# Smart Event Management System

A comprehensive desktop application for managing clubs, events, registrations, and QR-based attendance.

## Features

- **User Authentication**: Role-based login (Admin & Student)
- **Club Management**: Create and manage clubs
- **Event Management**: Add, view, and manage events
- **Student Registration**: Students can register for events
- **QR-based Attendance**: Check-in using QR tokens
- **Event Registrations**: View all registrations per event

## Prerequisites

- Java 17 or higher
- MySQL 8.x
- Maven 3.6+

## Database Setup

1. Open MySQL command prompt
2. Create the database by running the SQL script:
   ```sql
   mysql -u root -p < database.sql
   ```
   Or copy-paste the contents of `database.sql` into MySQL Workbench

3. Default credentials in the script:
   - Admin: admin@college.edu / admin123
   - Student: student@college.edu / student123

## Database Configuration

Update the database connection details in `DBConnection.java` if needed:
- URL: `jdbc:mysql://localhost:3306/event_club_db`
- Username: `root`
- Password: `root`

## Build Instructions

```bash
# Navigate to project directory
cd smartevent

# Clean and compile
mvn clean compile

# Package
mvn clean package

# Run the application
mvn exec:java@run
```

## Running the Application

### Option 1: Using Maven
```bash
mvn exec:java
```

### Option 2: Using Java directly
```bash
java -cp target/smartevent-1.0-SNAPSHOT.jar com.college.eventclub.LoginFrame
```

## Project Structure

```
smartevent/
├── src/main/java/com/college/eventclub/
│   ├── DBConnection.java
│   ├── LoginFrame.java
│   ├── AdminDashboardFrame.java
│   ├── StudentDashboardFrame.java
│   ├── ManageClubsFrame.java
│   ├── AddEventFrame.java
│   ├── ViewEventsFrame.java
│   ├── ViewRegistrationsFrame.java
│   ├── QRCheckInFrame.java
│   ├── StudentViewEventsFrame.java
│   ├── StudentMyRegistrationsFrame.java
│   ├── StudentMyQRCodesFrame.java
│   ├── Model Classes (User, Club, Event, Registration)
│   ├── DAO Classes (UserDAO, ClubDAO, EventDAO, RegistrationDAO, AttendanceDAO)
│   ├── Utilities (QRCodeUtil, EmailUtil)
│   └── ...
├── database.sql
├── pom.xml
└── README.md
```

## Database Schema

### Tables:
- **users**: User accounts with role-based access
- **clubs**: Event clubs
- **events**: Events organized by clubs
- **registrations**: Student registrations for events
- **attendance**: QR-based check-in records
- **feedback**: Event feedback from students

## User Roles

### Admin
- Add and manage clubs
- Create and manage events
- View all event registrations
- QR-based attendance check-in
- View attendance reports

### Student
- View available events
- Register for events
- View own QR codes
- View registration status
- Cancel registrations

## Default Login Credentials

**Admin Account:**
- Email: admin@college.edu
- Password: admin123

**Student Account:**
- Email: student@college.edu
- Password: student123

## Dependencies

- **MySQL Connector** (8.0.33): Database connectivity
- **ZXing** (3.5.1): QR code generation and reading
- **JavaMail** (1.6.2): Email notifications

## Troubleshooting

### Database Connection Error
- Ensure MySQL service is running
- Check username and password in `DBConnection.java`
- Verify database `event_club_db` exists

### Compilation Error
- Ensure Java 17+ is installed: `java -version`
- Ensure Maven is installed: `mvn -version`
- Run `mvn clean install` to download all dependencies

### Runtime Error
- Check application console for error messages
- Ensure all required dependencies are downloaded
- Verify database tables are created correctly

## Future Enhancements

- Email notifications for events and registrations
- Advanced reporting and analytics
- Event feedback system
- Mobile app integration
- Payment integration for paid events
- Automated reminders

## Support

For issues or questions, refer to the main menu and dashboard help sections.

---

**Version:** 1.0
**Author:** Event Management Team
**Last Updated:** December 2025
