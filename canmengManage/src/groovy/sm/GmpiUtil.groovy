/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sm
import grails.persistence.Event

/**
 * 用于template render
 * @author 王刚
 */
class GmpiUtil {

    static def renderDataFormat(domainClass,propertyName){
        def excludedProps = Event.allEvents.toList()
	def allowedNames = domainClass.persistentProperties*.name<<"id"<<"version"
	def props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) && it.type != null && !Collection.isAssignableFrom(it.type) }
        def str=props.collect{
            it.name+":"+propertyName+"."+it.name
        }.join(',')
        return "["+str+"]"
    }


    static def renderDatagridCloumns(domainClass,pluginManager,comparator){
        def excludedProps = Event.allEvents.toList() << 'version' << 'dateCreated' << 'lastUpdated'
	def persistentPropNames = domainClass.persistentProperties*.name
	boolean hasHibernate = pluginManager?.hasGrailsPlugin('hibernate')
	if (hasHibernate && org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsDomainBinder.getMapping(domainClass)?.identity?.generator == 'assigned') {
            persistentPropNames << domainClass.identifier.name
	}
	def props = domainClass.properties.findAll { persistentPropNames.contains(it.name) && !excludedProps.contains(it.name) }
	Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
        def collection=[]
        for (p in props) {
            if (p.embedded) {

            } else {
                collection<<renderFieldForProperty( domainClass,pluginManager,p)
            }
        }
        collection.join(",")
    }

    static def renderFieldForProperty(domainClass,pluginManager,property,prefix = ""){
        boolean hasHibernate = pluginManager?.hasGrailsPlugin('hibernate')
        boolean display = true
        boolean required = false
        def cp
        if (hasHibernate) {
            cp = domainClass.constrainedProperties[property.name]
            display = (cp ? cp.display : true)
            required = (cp ? !(cp.propertyType in [boolean, Boolean]) && !cp.nullable && (cp.propertyType != String || !cp.blank) : false)
        }
        def sb = new StringBuilder("")
        sb <<"{title:'${property.name}',field:'${property.name}',width:150,sortable:true"
        if (display){
            sb<<",hidden:false"
        }else{
            sb<<",hidden:true"
        }

        sb<<"}"
        sb as String
    }

    static def renderEasyuiForm(domainClass,pluginManager,comparator){
        def excludedProps = Event.allEvents.toList() << 'version' << 'dateCreated' << 'lastUpdated' <<"id"
        def persistentPropNames = domainClass.persistentProperties*.name
        boolean hasHibernate = pluginManager?.hasGrailsPlugin('hibernate')
        if (hasHibernate && org.codehaus.groovy.grails.orm.hibernate.cfg.GrailsDomainBinder.getMapping(domainClass)?.identity?.generator == 'assigned') {
            persistentPropNames << domainClass.identifier.name
        }
        def props = domainClass.properties.findAll { persistentPropNames.contains(it.name) && !excludedProps.contains(it.name) }
        Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))

        def count=0
        def sb = new StringBuilder("")
        props.eachWithIndex{ p, i ->
            count=i
            if (p.embedded) {

            } else {
                if(i%2==0)
                sb<<"<tr>\n "<<renderFieldForForm(domainClass,pluginManager,p)
                else if(i%2==1)
                sb<<renderFieldForForm(domainClass,pluginManager,p)<<"\n </tr>"
            }
        }
        if(count%2==0)
        sb<<"\n </tr>"

        sb as String
    }

    static def renderFieldForForm(domainClass,pluginManager,property,prefix=""){
        boolean hasHibernate = pluginManager?.hasGrailsPlugin('hibernate')
        boolean display = true
        boolean required = false
        def cp
        if (hasHibernate) {
            cp = domainClass.constrainedProperties[property.name]
            display = (cp ? cp.display : true)
            required = (cp ? !(cp.propertyType in [boolean, Boolean]) && !cp.nullable && (cp.propertyType != String || !cp.blank) : false)
        }
        def sb = new StringBuilder("")
        if (display){
            sb<<'\t\t<th>'<<"<g:message code=\"${domainClass.propertyName}.${property.name}.label\" default=\"${property.naturalName}\"/></th><td>" <<renderEditor(cp,domainClass,property)<<'</td>\n'
        }else{
        }

        sb as String
    }


    static def renderEditor(cp,domainClass,property){
        def sb=new StringBuilder("")
        if (property.type == Boolean || property.type == boolean)
        sb << renderBooleanEditor(cp,domainClass, property)
        else if (property.type && Number.isAssignableFrom(property.type) || (property.type?.isPrimitive() && property.type != boolean))
        sb << renderNumberEditor(cp,domainClass, property)
        else if (property.type == String)
        sb << renderStringEditor(cp,domainClass, property)
        else if (property.type == Date || property.type == java.sql.Date || property.type == java.sql.Time || property.type == Calendar)
        sb << renderDateEditor(cp,domainClass, property)
        else if (property.type == URL)
        sb << renderStringEditor(cp,domainClass, property)
        else if (property.type && property.isEnum())
        sb << renderEnumEditor(cp,domainClass, property)
        else
        sb<<renderStringEditor(cp,domainClass, property)
        sb as String
    }

    static def renderBooleanEditor(cp,domainClass, property){
        if(!cp){
            return "<input type=\"checkbox\" name=\"${property.name}\"/>"
        }else{
            def sb = new StringBuilder("")
            if('select'==cp.widget ){
                sb<<"<select name=\"${property.name}\" class=\"easyui-combobox\"  style=\"width:100px;\"> "
                sb<<'<option value="0">否</option>'<<'<option value="1">是</option>'
                sb<<"</select>"
            }else if('radio'==cp.widget ){
                sb<<'<input type="radio" value="0" name="'<<property.name<<'" />否'<<'<input type="radio" value="1" name="'<<property.name<<'" />是'
            }else {
                sb<<'<input type="checkbox" name="'<<property.name<<'" />'
            }
            sb as String
        }

    }
    static def renderStringEditor(cp,domainClass, property){
        if(!cp){
            return "<input  type='text' name=\"${property.name}\" />"
        }else{
            def required = (cp ? !(cp.propertyType in [boolean, Boolean]) && !cp.nullable && (cp.propertyType != String || !cp.blank) : false)
            def sb = new StringBuilder("")
            if('easyui-validatebox'==cp.widget){
                sb<<"<input name=\"${property.name}\" class=\"easyui-validatebox\" data-options=\" "
                sb<<" required:${required} "
                cp.attributes.each { k, v ->
                    sb << ", ${k}:${v} "
                }
                sb<<" \" />"
            }else if('textarea'==cp.widget || (cp.maxSize > 250 && !cp.password && !cp.inList)){
                sb << '<textarea  name="' << property.name << '"'
                sb << ' cols="40" rows="5"'
                sb << '/>'
            }else if (cp.inList) {
                sb<<"<select name=\"${property.name}\" class=\"easyui-combobox\"  style=\"width:100px;\"> "
                cp.inList.each{
                    sb<<"<option >${it}</option>"
                }
                sb<<"</select>"
            }else if (cp.password) {
                sb << "<input type=\"password\" name=\"${property.name}\" />"
            }else{
                sb<<"<input  type='text' name=\"${property.name}\" />"
            }
            sb as String
        }
    }
    static def renderDateEditor(cp,domainClass, property){
        if(!cp){
            return "<input class=\"easyui-datetimebox\" name=\"${property.name}\"  data-options=\"showSeconds:true\"  style=\"width:150px\"/>"
        }else{
            def required = (cp ? !(cp.propertyType in [boolean, Boolean]) && !cp.nullable && (cp.propertyType != String || !cp.blank) : false)
            def sb = new StringBuilder("<input  name=\"${property.name}\" style=\"width:150px\" data-options=\" ")
            sb<<" required:${required} "
            cp.attributes.each { k, v ->
                sb << " ${k}:${v} "
            }
            sb<<" \" "
            if(cp.widget)
            sb<<" class=\"${cp.widget}\""
            else
            sb<<" class=\"easyui-datetimebox\" "


            sb<<" />"
            sb as String
        }



    }
    static def renderEnumEditor(cp,domainClass, property){

    }
    static def renderSelectEditor(cp,domainClass, property){
        return "<input  name=\"${property.name}\" />"
    }
    static def renderNumberEditor(cp,domainClass, property){
        if(!cp)
        return "<input type=\"text\" name=\"${property.name}\" class=\"easyui-numberbox\" data-options=\"min:0,precision:0\"/>"
        else{
            def required = (cp ? !(cp.propertyType in [boolean, Boolean]) && !cp.nullable && (cp.propertyType != String || !cp.blank) : false)
            def sb = new StringBuilder("<input type=\"text\" name=\"${property.name}\" class=\"easyui-numberbox\" data-options=\" ")
            if(cp.range){
                sb<< " min:${cp.range.from},max:${cp.range.to}"
            }
            if(cp.min) sb<< " min:${cp.min}"
            if(cp.max) sb<< " max:${cp.max}"
            sb<<" required:${required} "
            if(cp.scale) sb<<" precision:${cp.scale}"
            cp.attributes.each { k, v ->
                sb << ",${k}:${v} "
            }

            sb<<"\" style=\"width:50\"/>"
        }
    }


}

