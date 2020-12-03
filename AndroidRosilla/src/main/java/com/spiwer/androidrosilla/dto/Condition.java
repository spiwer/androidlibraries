package com.spiwer.androidrosilla.dto;

/**
 *
 * @author spiwer.com - Herman Leonardo Rey Baquero - leoreyb@gmail.com
 */
public class Condition
{

  private String name;
  private String operator;
  private Object Value;

  public Condition()
  {
  }

  public Condition(String name, String operator, Object Value)
  {
    this.name = name;
    this.operator = operator;
    this.Value = Value;
  }

  public String getName()
  {
    return name;
  }

  public Condition setName(String name)
  {
    this.name = name;
    return this;
  }

  public String getOperator()
  {
    return operator;
  }

  public Condition setOperator(String operator)
  {
    this.operator = operator;
    return this;
  }

  public Object getValue()
  {
    return Value;
  }

  public Condition setValue(Object Value)
  {
    this.Value = Value;
    return this;
  }

  public String getConditionSelect()
  {
    return name + " " + operator + " :" + name;
  }

  public String getConditionUpdate()
  {
    return name + " " + operator + " :c" + name;
  }

}
