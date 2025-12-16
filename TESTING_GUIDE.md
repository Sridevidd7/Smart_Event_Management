# Testing Guide - Smart Event Management System

## Unit Testing Checklist

### Database Connection Tests
- [ ] Test `DBConnection.testConnection()` returns true
- [ ] Verify connection closes properly
- [ ] Verify error handling for invalid credentials

### User Authentication Tests
- [ ] Login with valid admin credentials succeeds
- [ ] Login with valid student credentials succeeds
- [ ] Login with invalid credentials fails
- [ ] Email field validation works
- [ ] Password field validation works

### Club Management Tests
- [ ] Add club with valid data succeeds
- [ ] Add club with empty name fails
- [ ] Duplicate club name is prevented
- [ ] View clubs shows all clubs
- [ ] Delete club works
- [ ] Clubs appear in dropdown for event creation

### Event Management Tests
- [ ] Add event with all valid fields succeeds
- [ ] Event without club selection fails
- [ ] Event without date fails
- [ ] Event without venue fails
- [ ] Event date validation works (YYYY-MM-DD format)
- [ ] Events sorted by date in view
- [ ] Delete event removes from database
- [ ] Events filtered by club in dropdown

### Registration Tests
- [ ] Student can register for available event
- [ ] Duplicate registration is prevented
- [ ] QR token is generated for each registration
- [ ] QR token is unique
- [ ] Registration status defaults to REGISTERED
- [ ] Student cannot register for same event twice

### QR Check-In Tests
- [ ] Valid QR token marks attendance
- [ ] Check-in timestamp is recorded
- [ ] Duplicate check-in is prevented
- [ ] Registration status changes to CHECKED_IN
- [ ] Invalid QR token shows error
- [ ] Check-in requires exact QR token match

---

## Integration Testing Checklist

### Admin Workflow
1. **Complete Admin Flow**
   - [ ] Login as admin
   - [ ] Add new club via "Manage Clubs"
   - [ ] Create event with new club via "Add Event"
   - [ ] View event in "View Events"
   - [ ] Delete event
   - [ ] Check-in student with QR via "QR Check-In"
   - [ ] View registrations for event
   - [ ] Logout successfully

### Student Workflow
1. **Complete Student Flow**
   - [ ] Login as student
   - [ ] View available events
   - [ ] Register for event
   - [ ] See QR token in confirmation
   - [ ] View my registrations
   - [ ] View my QR codes
   - [ ] See check-in status
   - [ ] Cancel registration
   - [ ] Logout successfully

### Admin Check-In Workflow
1. **Check-In Process**
   - [ ] Admin opens QR Check-In
   - [ ] Student gets QR token from registration
   - [ ] Admin enters QR token
   - [ ] System confirms check-in
   - [ ] Attendance timestamp is recorded
   - [ ] Registration status updates to CHECKED_IN
   - [ ] Prevent duplicate check-in attempt

---

## Database Integrity Tests

### Foreign Key Validation
- [ ] User IDs in clubs table exist in users table
- [ ] Club IDs in events table exist in clubs table
- [ ] Event IDs in registrations exist in events table
- [ ] User IDs in registrations exist in users table
- [ ] Registration IDs in attendance exist in registrations table

### Unique Constraint Tests
- [ ] Cannot create two users with same email
- [ ] Cannot create two clubs with same name
- [ ] Cannot create duplicate registration for user+event
- [ ] QR tokens are globally unique

### Data Type Tests
- [ ] Event dates are stored as DATE type
- [ ] Timestamps are properly stored and retrieved
- [ ] IDs are integers
- [ ] Text fields handle special characters
- [ ] Email fields validate email format

---

## GUI Usability Tests

### LoginFrame
- [ ] Title bar is correct
- [ ] Email and password fields are editable
- [ ] Login button is clickable
- [ ] Clear button clears fields
- [ ] Database connection test passes
- [ ] Error message displays for invalid login
- [ ] Success message displays for valid login
- [ ] Window is properly centered
- [ ] Window size is appropriate

### AdminDashboardFrame
- [ ] Dashboard title shows correctly
- [ ] Welcome message shows user name
- [ ] All 6 buttons are visible and clickable
- [ ] Each button opens corresponding frame
- [ ] Logout button works
- [ ] Window closes properly

### ManageClubsFrame
- [ ] Can input club name
- [ ] Can input description
- [ ] Add Club button works
- [ ] Clear button clears fields
- [ ] Table shows all clubs
- [ ] Table updates after adding club
- [ ] Can delete club
- [ ] Table is scrollable if many clubs

### AddEventFrame
- [ ] Event title field works
- [ ] Club dropdown loads all clubs
- [ ] Date field shows current date
- [ ] Date validation works
- [ ] Venue field works
- [ ] Description area allows multiline text
- [ ] Save button saves to database
- [ ] Clear button clears fields
- [ ] Success message displays
- [ ] Error messages are informative

### ViewEventsFrame
- [ ] Events are sorted by date
- [ ] All event columns display correctly
- [ ] Can select event from table
- [ ] Can delete selected event
- [ ] Refresh button reloads events
- [ ] Deletion confirmation appears
- [ ] Table is scrollable

### QRCheckInFrame
- [ ] QR token input field works
- [ ] Check-In button is clickable
- [ ] Result area displays check-in status
- [ ] Result shows user and event name
- [ ] Result shows check-in timestamp
- [ ] Duplicate check-in message appears
- [ ] Invalid token message appears

---

## Performance Tests

- [ ] Application starts within 5 seconds
- [ ] Login completes within 2 seconds
- [ ] Switching frames is smooth
- [ ] Table with 100+ events loads smoothly
- [ ] Database queries complete quickly
- [ ] No memory leaks on repeated operations
- [ ] No lag when entering data

---

## Security Tests

- [ ] Passwords are not displayed in plaintext
- [ ] SQL injection attempts are prevented
- [ ] Invalid SQL doesn't cause errors
- [ ] Cross-user data is not visible
- [ ] Student cannot access admin functions
- [ ] Admin credentials are required for admin functions

---

## Error Handling Tests

- [ ] Database connection failure shows error
- [ ] Invalid date format shows error message
- [ ] Empty required fields show validation error
- [ ] Duplicate entries show appropriate error
- [ ] Delete operations show confirmation
- [ ] Network errors are handled gracefully

---

## Test Scenarios

### Scenario 1: New Club with Events
**Setup:**
- Admin logged in

**Steps:**
1. Add club "Tech Club"
2. Add event "Hackathon" to Tech Club
3. Add event "Coding Contest" to Tech Club
4. View events

**Expected Result:**
- Both events appear with Tech Club
- Can view all events

### Scenario 2: Student Registration and Check-In
**Setup:**
- Student and Admin both have accounts
- Event exists

**Steps:**
1. Student registers for event
2. Gets QR token
3. Admin uses QR token to check in student
4. Verify attendance recorded

**Expected Result:**
- Registration created
- QR token generated
- Check-in successful
- Attendance timestamp recorded

### Scenario 3: Duplicate Prevention
**Setup:**
- Student account
- Event exists

**Steps:**
1. Register for event (first time)
2. Try to register again
3. Try to check in twice with same token

**Expected Result:**
- First registration succeeds
- Second registration fails with message
- Duplicate check-in prevented

### Scenario 4: Complete Admin Workflow
**Setup:**
- Admin account

**Steps:**
1. Add 3 clubs
2. Create 5 events (mix of clubs)
3. View all events
4. Delete 1 event
5. Add 1 more event

**Expected Result:**
- All operations complete successfully
- Data persists in database
- No errors

---

## Regression Testing

After any changes, verify:
- [ ] All existing tests still pass
- [ ] No new errors introduced
- [ ] Database schema still valid
- [ ] All DAOs still work
- [ ] UI elements still responsive
- [ ] FK relationships maintained

---

## Final Verification

Before deployment, ensure:
- ✅ All compilation successful
- ✅ No runtime errors
- ✅ Database connections working
- ✅ All CRUD operations functional
- ✅ QR generation working
- ✅ Login system secure
- ✅ Role-based access working
- ✅ UI responsive and user-friendly
- ✅ Documentation complete
- ✅ Error messages helpful

---

## Test Results Summary

Date: __________
Tester: __________

| Component | Status | Notes |
|-----------|--------|-------|
| Database Connection | ☐ Pass ☐ Fail | |
| User Authentication | ☐ Pass ☐ Fail | |
| Club Management | ☐ Pass ☐ Fail | |
| Event Management | ☐ Pass ☐ Fail | |
| Student Registration | ☐ Pass ☐ Fail | |
| QR Check-In | ☐ Pass ☐ Fail | |
| Admin Dashboard | ☐ Pass ☐ Fail | |
| Student Dashboard | ☐ Pass ☐ Fail | |
| Data Integrity | ☐ Pass ☐ Fail | |
| Performance | ☐ Pass ☐ Fail | |
| Security | ☐ Pass ☐ Fail | |
| Error Handling | ☐ Pass ☐ Fail | |

---

**Overall Status:** ☐ Ready for Production ☐ Needs Fixes

---

For any failures, document:
1. Component that failed
2. Steps to reproduce
3. Expected vs actual behavior
4. Screenshots (if applicable)

---

Last Updated: December 2025
