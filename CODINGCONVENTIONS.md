# Coding conventions

All contributors should follow these rules for coding :

* Use English for names of variables, classes, methods, packages, ...

* Use understandable names (not only to yourself but to the whole team)

* Use the following case for variables, methods (except constructors) and classes :

```
public class ClassName {

    private int variableName;

    public void methodName() {
      ...
    }

}
```

* Follow the same packages structure in src/main and src/test

* Divide your classes in severals parts : attributes, constructors, getters and setters, utility methods, ...

* Add a description to classes and methods :

```
import ...

/**
  * Class description
  *
**/
public class ClassName {

  //***** Attibutes *****
  private int attributeName;    //Attibute description
  ...

  /***** Constructors *****
  public ClassName() {
    ...
  }

  /***** Getters and setters *****
  void setAttributeName(..) {
    ...
  }
}

```
