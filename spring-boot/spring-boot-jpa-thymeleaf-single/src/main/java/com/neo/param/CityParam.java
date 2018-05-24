package com.neo.param;

import com.neo.entity.Base;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class CityParam extends Base{
    //    @NotEmpty(message="ID不能为空")
    private long id;

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    @Max(value = 999999999, message = "不能大于999999999")
    @Min(value= 1 ,message= "不能小于1！" )
    private long pid;

    @NotEmpty(message="名称不能为空")
    private String name;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





}
