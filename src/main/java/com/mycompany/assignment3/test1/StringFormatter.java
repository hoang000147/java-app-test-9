/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.assignment3.test1;

/**
 *
 * @author Asus
 */
import java.util.*;

/**
 * A string formatter is divided into a number of parts, which is either a string literal, an index form, 
 * or an index form with alignment and width. <br/><br/>
 * 
 * For example, the formatter <code>"I am {0}. Would you like {1} and {2}?"</code>
 * is made of 7 parts:
 * <ol>
 *   <li>a string literal <code>"I am "</code></li>
 *   <li>an index form <code>"{0}"</code></li>
 *   <li>a string literal <code>". Would you like "</code></li>
 *   <li>an index form <code>"{1}"</code></li>
 *   <li>a string literal <code>" and "</code></li>
 *   <li>an index form <code>"{2}"</code></li>
 *   <li>a string literal <code>"?"</code></li>
 * </ol>
 * This formatter is constructed as:
 * <pre>
 * <code>
 * StringFormatter sf = (new StringFormatter())
 *                       .add("I am ")
 *                       .add(0)
 *                       .add(". Would you like ")
 *                       .add(1)
 *                       .add(" and ")
 *                       .add(2)
 *                       .add("?");
 * </code>
 * </pre>
 * The code
 * <pre>
 * <code>
 * System.out.println(sf.format("Sam", "green eggs", "ham"));
 * System.out.println(sf.format("Joe", "red bread", "jam")); 
 * </code>
 * </pre>
 * will produce the following output:
 * <pre>
 * <code>
 * I am Sam. Would you like green eggs and ham?
 * I am Joe. Would you like red bread and jam?
 * </code>
 * </pre>
 * 
 * Another example, the formatter <code>"{0L15}{1C10}{2C25}{3R10}"</code>
 * is made of 4 parts:
 * <ol> 
 *   <li>an index form with alignment and width<code>"{0L15}"</code></li>
 *   <li>an index form with alignment and width<code>"{1C10}"</code></li>
 *   <li>an index form with alignment and width<code>"{2C25}"</code></li>
 *   <li>an index form with alignment and width<code>"{3R10}"</code></li>
 * </ol>
 * This formatter is constructed as:
 * <pre>
 * <code>
 * StringFormatter sf = (new StringFormatter())
 *                       .add(0, StringFormatter.Alignment.L, 15)
 *                       .add(1, StringFormatter.Alignment.C, 10)
 *                       .add(2, StringFormatter.Alignment.C, 25)
 *                       .add(3, StringFormatter.Alignment.R, 10);
 * </code>
 * </pre>
 * The code
 * <pre>
 * <code>
 * System.out.println(sf.format("Element", "Symbol", "Atomic number", "Period"));
 * System.out.println(sf.format("Lithium", "Li", "3", "2"));
 * System.out.println(sf.format("Sodium", "Na", "11", "3"));
 * System.out.println(sf.format("Potassium", "K", "19", "4"));
 * System.out.println(sf.format("Rubidium", "Rb", "37", "5"));
 * System.out.println(sf.format("Caesium", "Cs", "55", "6"));
 * System.out.println(sf.format("Francium", "Fr", "87", "7"));  
 * </code>
 * </pre>
 * will produce the following output:
 * <pre>
 * <code>
 * Element          Symbol        Atomic number          Period
 * Lithium            Li                3                     2
 * Sodium             Na               11                     3
 * Potassium          K                19                     4
 * Rubidium           Rb               37                     5
 * Caesium            Cs               55                     6
 * Francium           Fr               87                     7
 * </code>
 * </pre>
 * 
 * If the string argument has length longer than the specified width then the input string is truncated to
 * have the specified width.
 * 
 * By default, null value will produce the string <code>"null"</code>.
 * To set a customized null value, use the method <code>nullValue(String value)</code>.
 * 
 * @author Joseph Tonien
 *
 */

public class StringFormatter {

  public enum Alignment{L, C, R}



  /**
   * Represents a part of the string formatter,
   * which is either a string literal, an index form, 
   * or index form with alignment and width.
   */
  interface Part{
    String format(String... args);
  }



  /**
   * The output string for null value.
   */
  private String nullValue = "null";

  /**
   * A string formatter is divided into a number of parts.
   */
  private List<Part> partList = new ArrayList<Part>();





  /**
   * Set the value for null string.
   * @param value the value for null string
   * @return this object
   */

  public StringFormatter nullValue(String value) {
    nullValue = value;
    return this;
  }



  /**
   * Add a string literal part.
   * 
   * @param literal a string
   * @return this object which has been appended with the string literal part.
   */

  public StringFormatter add(String literal) {

    partList.add(new Part() {
      @Override
      public String format(String... args) {
        return literal;
      }
    });

    return this;
  }

  /**
   * Add an index form.
   * 
   * @param index the index of the string argument
   * 
   * @return this object.
   */
  public StringFormatter add(int index) {

    partList.add(new Part() {
      @Override
      public String format(String... args) {
        if(args[index] == null) {
          return nullValue;
        }

        return args[index];
      }
    });

    return this;
  }

  /**
   * Add an index form with alignment and width.
   * 
   * @param index the index of the string argument
   * @param alignment the alignment
   * @param width the output width
   * @return this object.
   */
  public StringFormatter add(int index, Alignment alignment, int width) {

    partList.add(new Part() {
      @Override
      public String format(String... args) {
        String arg = args[index];

        if(arg == null) {
          arg = nullValue;
        }

        if(arg.length() == width) {
          return arg;
        }else if(arg.length() > width) {
          return arg.substring(0, width);
        }

        int frontWidth = 0;
        int backWidth = 0;

        if(alignment == Alignment.C) {
          frontWidth = (width - arg.length())/2;
          backWidth = width - arg.length() - frontWidth;
        }else if(alignment == Alignment.L) {
          frontWidth = 0;
          backWidth = width - arg.length();
        }else if(alignment == Alignment.R) {
          backWidth = 0;
          frontWidth = width - arg.length();
        }

        StringBuffer sb = new StringBuffer(width);

        for(int i=0;i<frontWidth;i++) {
          sb.append(" ");
        }

        sb.append(arg);

        for(int i=0;i<backWidth;i++) {
          sb.append(" ");
        }

        return sb.toString();
      }
    });

    return this;
  }



  /**
   * Format the strings.
   * 
   * @param args list of strings
   * 
   * @return formatted string.
   */

  public String format(String... args) {

    StringBuffer sb = new StringBuffer();

    for(Part part: partList) {
      sb.append(part.format(args));
    }

    return sb.toString();
  }


  /*public static void main(String[] args) {

    System.out.println("Example 1:");
    
    StringFormatter sf = (new StringFormatter())
        .add("I am ")
        .add(0)
        .add(". Would you like ")
        .add(1)
        .add(" and ")
        .add(2)
        .add("?");



    System.out.println(sf.format("Sam", "green eggs", "ham")); 
    System.out.println(sf.format("Joe", "red bread", "jam")); 
    
    System.out.println();
    System.out.println();
    System.out.println("Example 2:");

    sf = (new StringFormatter())
        .add(0, StringFormatter.Alignment.L, 10)
        .add(1, StringFormatter.Alignment.C, 10)
        .add(2, StringFormatter.Alignment.C, 10)
        .add(3, StringFormatter.Alignment.C, 10)
	.add(4, StringFormatter.Alignment.C, 10)
	.add(5, StringFormatter.Alignment.C, 10)
        .add(6, StringFormatter.Alignment.R, 5);
    
    System.out.println(sf.format("Element", "Symbol", "Atomic number", "Period"));
    System.out.println(sf.format("Lithium", "Li", "3", "2"));
    System.out.println(sf.format("Sodium", "Na", "11", "3"));
    System.out.println(sf.format("Potassium", "K", "19", "4"));
    System.out.println(sf.format("Rubidium", "Rb", "37", "5"));
    System.out.println(sf.format("Caesium", "Cs", "55", "6"));
    System.out.println(sf.format("Francium", "Fr", "87", "7"));  
  }*/

}
