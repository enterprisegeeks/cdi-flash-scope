CDI Flash Scope
-----------------

## Abstraction

This provides CDI custom flash scope that is active during POST request and next Redirect Request.
(e.g, Post-Then-Redirect)

## Requires

+ JDK 1.7 or later
+ JavaEE 7
    + CDI 1.1
    + Servlet 3

## Usage

1. add class pass `cdi-flash-scope-xx.jar`
  + jar file is [mvn-repo branch](https://github.com/enterprisegeeks/cdi-flash-scope/tree/mvn-repo)
  + if you use maven, please add repository and dependency.

    ```xml
        <!-- add repository -->
        <repositories>
            <!-- -->
            <repository>
                <id>whatever you like</id>
                <name>whatever you like</name>
                <url>https://raw.github.com/enterprisegeeks/cdi-flash-scope/mvn-repo</url>
            </repository>
            <!-- -->
        </repositories>

        <!-- add dependency -->
        <dependencies>
            <!-- -->
            <dependency>
                <groupId>com.github.enterprisegeeks</groupId>
                <artifactId>cdi-flash-scope</artifactId>
                <version>0.1</version>
            </dependency>
            <!-- -->
        <dependencies>
    ```

2. You can use `@FlashScoped` Annotation for bean classes.

## Example, Test
see source in [cid-flash-scope-test](https://github.com/enterprisegeeks/cdi-flash-scope/tree/master/cdi-flash-scope-test) 

