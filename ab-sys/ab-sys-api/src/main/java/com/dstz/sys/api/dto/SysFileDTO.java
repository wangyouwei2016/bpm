package com.dstz.sys.api.dto;

import java.io.Serializable;

/**
 * @author jinxia.hou
 * @Name SysFileDTO
 * @description:
 * @date 2022/2/249:37
 */
public class SysFileDTO implements Serializable {


    private static final long serialVersionUID = 645973812881769440L;
    protected String id;
    /**
     * 附件名
     */
    private String name;
    /**
     * <pre>
     * 这附件用的是上传器
     * 具体类型可以看 IUploader 的实现类
     * </pre>
     */
    private String uploader;
    /**
     * <pre>
     * 路径，这个路径能从上传器中获取到对应的附件内容
     * 所以也不一定是路径，根据不同上传器会有不同值
     * </pre>
     */
    private String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getPath() {
        return path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
