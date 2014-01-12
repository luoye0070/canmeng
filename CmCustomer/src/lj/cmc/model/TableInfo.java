package lj.cmc.model;

public class TableInfo {
	public long id;
	//饭店ID
    public long restaurantId;
    //桌名
    public String name;
    //最少人数
    public int minPeople;
    //最大人数
    public int maxPeople;
    //是否多单共桌
    public boolean canMultiOrder=false;
    //是否支持预订
    public boolean canReserve=true;
    //描述
    public String description;
    //是否有效
    public boolean enabled=true;
}
