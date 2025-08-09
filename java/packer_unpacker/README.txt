
# File Packer Unpacker

## Overview
The File Packer Unpacker is a Java-based project designed to pack multiple files into a single file (with metadata) and unpack them back when required. 
It is useful for process monitoring, data backup, and secure file transfer.

## Features
- **Packing Activity**:
  - Accepts a directory name and output file name from the user.
  - Creates a packed file containing metadata (file name, size, checksum) and encrypted file data.
  - Generates a log file in the system directory for each packed file.
  - Displays a packing report after completion.

- **Unpacking Activity**:
  - Accepts a packed file name from the user.
  - Uses authentication (e.g., Magic Number) to verify file integrity.
  - Reads metadata to recreate original files.
  - Displays an unpacking report after completion.

- **Security Features**:
  - Data encryption for secure storage and transfer.
  - MD5 checksum validation to ensure file integrity.
  - Primary header verification for additional security.

## Technology Stack
- **Frontend**: Java Swing (GUI)
- **Backend**: Java
- **Platform**: Cross-platform (Windows NT, Linux)
- **Processor Requirement**: Intel 32-bit processor

## Usage Instructions
1. **Authentication**:
   - On launch, the application requests username and password.
   - Default credentials: `MarvellousAdmin` / `MarvellousAdmin`.
   - Three attempts allowed; otherwise, the program exits.
   - Thread-based check ensures password length ≥ 8 and alerts if Caps Lock is on.

2. **Packing**:
   - Select the `Pack` option.
   - Enter the directory name and packed file name.
   - The application encrypts and writes metadata and data into the packed file.

3. **Unpacking**:
   - Select the `Unpack` option.
   - Enter the packed file name.
   - The application verifies the file, then recreates original files from metadata.

## Purpose
- Combine multiple files into one to save space and reduce file management complexity.
- Secure data with encryption and checksum validation.
- Maintain logs for audit and reference.

## Expected Interview Questions
1. Explain the flow and working of your project.
2. What is packing and unpacking?
3. How does encryption work in your project?
4. How does packing save memory?
5. Why choose Java for development?
6. What is MD5 checksum and why is it used?
7. How are files recreated during unpacking?
8. How is logging implemented?
9. What UI is provided?
10. What types of files are supported?

© Marvellous Infosystems
