# Ransom Note Checker

This Clojure program determines if a ransom note can be constructed from letters found in a magazine. The program reads a message from a file and checks if the magazine file contains enough letters to match the message, processed in chunks to efficiently handle large files.

## Features

- **Efficient File Reading**: Processes the magazine file in manageable chunks to handle large files (potentially several GB).
- **Non-Whitespace Character Counting**: Ignores whitespace characters in both the message and magazine.
- **Error Handling**: Provides clear error messages for file reading issues and invalid inputs.

## Outputs

- The program prints `true` if the ransom note can be constructed from the magazine.
- The program prints `false` if it cannot.
- Errors during execution will display messages indicating the nature of the issue, such as:
    - **File Not Found**: The specified file path does not exist.
    - **Invalid File Type**: The provided file is not a `.txt` file.
    - **Empty File**: The message file is empty.
    - **File Too Large**: The message file exceeds the maximum allowed size.
    - **Reading Errors**: Issues encountered while reading the magazine file.

## Edge Cases

- **Empty Message File**: If the message file is empty, the program returns `true` since no letters are needed.
- **Empty Magazine File**: If the magazine file is empty, the program returns `false` since there are no letters available.
- **Non-Whitespace Characters Only**: Only non-whitespace characters are counted for both the message and magazine.

## Installation Instructions

To run this program, you will need to have Clojure installed. Follow the instructions below:

### Prerequisites

To run Clojure, you need to install Java (version 11 or later). You can verify the installation by running:
  ```bash
  java -version
  ```

### Install Clojure

The recommended way to install Clojure is through the Clojure CLI tools. Follow these steps:
1. Download the latest Clojure installer from the [official Clojure website](https://clojure.org/guides/install_clojure).
2. Follow the installation instructions specific to your operating system.

### Verify Installation
After installing, verify that Clojure is correctly installed by running:

```bash
clojure -S --version
```

## Usage

To run the ransom note program or the tests, you can use the following commands:

### Running the Ransom Note Program

To execute the ransom note program, use the following command:

```bash
./run_ransom_note.sh <message-file> <magazine-file>
```

Replace `<message-file>` with the path to your message file and `<magazine-file>` with the path to your magazine file. 
This script will check if the ransom note can be constructed from the characters in the magazine file.

### Running Functional Tests

To run the functional tests for the ransom note program, use the following command:

```bash
./run_tests.sh
```

This script will execute the tests defined in `test/ransom_note/core_test.clj`, allowing you to verify 
the functionality and correctness of the program.

For testing cases where the message or magazine files are larger than 1MB, 
I am using local files that are excluded from Git:
```
resources/magazine_big.txt
resources/magazine_midlesize_true.txt
resources/magazine_midlesize_false.txt
```
If you want to conduct functional tests on large files, please create your own files with these names in 
the `resources` directory and uncomment the corresponding tests in `core_test.clj`.


### TODO

1. Implement unit tests.
2. Implement error tests.
3. Enhance logging for better debugging and tracking.




