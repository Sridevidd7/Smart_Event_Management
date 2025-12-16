#!/bin/bash
# Installation and Run Script for Smart Event Management System

echo "================================================"
echo "Smart Event Management System - Setup Script"
echo "================================================"
echo ""

# Check Java
echo "[1/5] Checking Java installation..."
if ! command -v java &> /dev/null; then
    echo "ERROR: Java not found. Please install Java 17 or higher."
    exit 1
fi
java -version

# Check Maven
echo ""
echo "[2/5] Checking Maven installation..."
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven not found. Please install Maven."
    exit 1
fi
mvn --version

# Check MySQL
echo ""
echo "[3/5] Checking MySQL installation..."
if ! command -v mysql &> /dev/null; then
    echo "ERROR: MySQL not found. Please install MySQL 8.x"
    exit 1
fi
mysql --version

# Build Project
echo ""
echo "[4/5] Building project..."
mvn clean compile
if [ $? -ne 0 ]; then
    echo "Build failed. Check the output above for errors."
    exit 1
fi

echo ""
echo "[5/5] Setup complete!"
echo ""
echo "================================================"
echo "Next Steps:"
echo "================================================"
echo ""
echo "1. Setup Database:"
echo "   mysql -u root -p < database.sql"
echo ""
echo "2. Run the application:"
echo "   mvn exec:java"
echo ""
echo "3. Login with:"
echo "   Email: admin@college.edu"
echo "   Password: admin123"
echo ""
echo "================================================"

read -p "Press Enter to exit..."
