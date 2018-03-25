# Systematic Literature Review Tool
This is a tool that presents each paper in a corpus of search query results, presenting the user the option to either
say "yes" and save the paper for further exploration, "no" and disgard the paper, or "exit" and save the current session
ready to return at a later date.

[![Build Status](https://travis-ci.org/JonoCX/literature-review-tool.svg?branch=master)](https://travis-ci.org/JonoCX/literature-review-tool)

There is an expectation that your CSV file will contain the following information about each paper; title, authors,
year, abstract, and keywords. All named in in the same case.

## Installation

```sh
mvn clean package && cd target
```

### Usage
```sh
java -jar literature-review-tool.jar [-file <arg>] [-resume]
```

### First Session
```sh
java -jar literature-review-tool.jar -file "[location]"
```

### Resuming a session
The presumption here is that the original file is in the same location. This operation will overwrite the currently saved
and disregarded files (updating them accordingly).
```sh
java -jar literature-review-tool.jar -file "[location]" -resume
```

## Meta

Jonathan Carlton – [@JonoCX](https://twitter.com/JonoCX)

[https://github.com/JonoCX/literature-review-tool](https://github.com/JonoCX/)

