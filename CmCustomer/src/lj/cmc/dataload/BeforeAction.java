package lj.cmc.dataload;

public abstract class BeforeAction {

	/*************************************
	 *从网络获取数据之前进行的动作 
	 *@return 返回值大于等于0将继续执行数据交互操作，返回值小于0将不执行数据交互操作并返回
	 ***********************************/
	public abstract int doSome();
}
