edn-pojos
=========

Solution for unmarshalling Java objects out of edn format data

### Description

**edn-format** (https://github.com/edn-format/edn) is a great solution for serialising data, 
using Clojure's Lisp s-expressions as the basic format.

**edn-pojos** allows objects represented into this format to be efficiently parsed into
Java objects. You can create a custom parser to read edn data into any Java POJO format you like.

edn-pojo build on the scanner and parser implementations from **edn-java**