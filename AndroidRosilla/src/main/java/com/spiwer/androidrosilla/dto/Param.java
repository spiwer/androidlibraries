/*
 * To change this license app_header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spiwer.androidrosilla.dto;

import java.util.HashMap;

/**
 *
 * @author spiwer.com - Herman Leonardo Rey Baquero - leoreyb@gmail.com
 *
 * @param <G>
 * @param <C>
 */
@SuppressWarnings("serial")
public class Param<G, C> extends HashMap<G, C>
{

  public Param<G, C> add(G name, C value)
  {
    put(name, value);
    return this;
  }

}
