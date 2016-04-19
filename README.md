<h2>Answer Seeker</h2>
Answer Seeker is a programming question search engine I built in 2014. It uses Jsoup to search http://www.stackoverflow.com and http://www.codeproject.com for a given question. Sorts and ranks top answers to the search term and
returns them with proper html formatting so that if desired the output can be used to create a html page. 

  
<h2>Usage</h2>
<p>Created using JDK 8 and <a href="http://www.jsoup.org/download"> Jsoup 1.8.1 </a> . Results may vary using older versions of Jsoup or JDK</p>
To build a runnable .jar file using Eclipse
<ol><li>Create a new Java project and add the Jsoup.jar to your project</li>
<li>Create a package named answerseeker and add the relevant files to the answerseeker package</li>
<li>choose file->export->Runnable Jar</li></ol>
To run from the command line, type “java -jar name_of_built_jar”

<h2>License</h2>
Copyright (c) 2016  Mark Otten

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
</p>