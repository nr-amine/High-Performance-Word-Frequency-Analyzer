# Word Frequency Analyzer (Top-K Words)

## Overview
This repository contains a Java-based text analysis tool designed to efficiently extract the top $k$ most frequent words from large text files. 

The primary objective of this project was to explore data structure efficiency and algorithmic optimization by comparing custom Java implementations against native Unix pipeline commands.

## Performance Benchmarks
![Performance Benchmark vs k](benchmark_k_graph.png)

## Implementation Evolution

The project was developed in three main iterations to overcome sequential performance bottlenecks:

### 1. Custom Sorted Array (`SortedArrayMap`)
* The project began with a naive implementation, which was later upgraded to a Sorted Array implementation.
* A major breakthrough was discovering the "index trick".
* When `binarySearch` does not find an item, it returns the exact position where the item should be inserted.
* Utilizing this negative return value for direct insertions provided a 4x speed boost compared to the initial version.
* However, a scalability wall was hit: asking for the top 5000 words caused execution time to exceed 20 seconds.

### 2. Hash Table Transition (`HashMapWordFreq`)
* To resolve the scalability issues, the underlying data structure was switched to a `HashMap`.
* This transition provided a significant performance bump.
* Despite this, the program still struggled to outperform the native Unix shell command.

### 3. Optimized Hybrid Approach (`HashStreamHybrid`)
* Profiling revealed that standard Java tools like `split()` and `replaceAll()` were the primary bottlenecks.
* These were replaced with a manual C-style loop that reads the file character by character.
* Combining this manual parsing loop with Java Streams for sorting allowed the program to execute faster than the Unix shell command itself.

## Usage

```bash
# Compile the optimized hybrid analyzer
javac HashStreamHybrid.java

# Run the analyzer on a text file to find the top 10 words
java HashStreamHybrid textfile.txt 10

# Run the analyzer for the top 5000 words
java HashStreamHybrid textfile.txt 5000
