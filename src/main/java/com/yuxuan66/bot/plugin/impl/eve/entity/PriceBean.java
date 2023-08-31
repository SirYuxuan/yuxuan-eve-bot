package com.yuxuan66.bot.plugin.impl.eve.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceBean {

   private int id;
   private long buy;
   private long sell;
}