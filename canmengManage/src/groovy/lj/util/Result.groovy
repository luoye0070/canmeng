/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lj.util
import grails.converters.JSON
/**
 *返回结果
 * @author think
 */
public class Result {
    public String msg
    public Boolean success=false
    public def obj=null

    def toJson(){
        def res=[success:success,msg:msg,obj:obj]
        return res as JSON
    }

}

