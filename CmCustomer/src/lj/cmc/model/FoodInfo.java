package lj.cmc.model;

public class FoodInfo {
	
	public long id;//ID
	
    public long restaurantId;//饭店ID

    public String name;//菜名

    public Double price=0.0;//价格
 
    public Double originalPrice;//原价

    public String image;//图片

    public Boolean canTakeOut=false;//是否支持外卖true支持false不支持

    public String description;//描述

    public Boolean enabled=true;//状态（有效性）true有效false无效

    public Integer countLimit=0;//每天限量0表示不限量

    public Boolean isSetMeal=false;//是否套餐

    public int sellCount=0;//当天销量

    public int totalSellCount=0;//累计销量
}
