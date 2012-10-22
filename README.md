edn-pojos
=========

Solution for unmarshalling Java objects out of **edn** format data

### Description

**edn-format**  is a great solution for serialising data, 
using Clojure's Lisp s-expressions as the basic format. Main advantages of edn:

 - A well-specified format (see https://github.com/edn-format/edn)
 - A rich set of data types (an improvement over JSON)
 - Built-in extensibility to allow custom data types
 - Compatibility with the Clojure reader

**edn-pojos** allows objects represented into this format to be efficiently parsed into
Java objects. You can create a custom parser to read edn data into any Java POJO format you like.

**edn-pojos** build on the scanner and parser implementations from **edn-java**

### Basic Usage

TODO

###Build Status###

[![Build Status](https://secure.travis-ci.org/mikera/edn-pojos.png)](http://travis-ci.org/mikera/edn-pojos)