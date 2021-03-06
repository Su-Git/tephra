package org.lpw.tephra.ctrl.http.upload;

/**
 * @author lpw
 */
public interface UploadListener {
    /**
     * 获取监听器key；监听器key必须与上传字段名一致。
     *
     * @return 监听器key，支持正则表达式。
     */
    String getKey();

    /**
     * 验证是否允许上传。
     *
     * @param key         上传文件key。
     * @param contentType 文件类型。
     * @param name        文件名。
     * @return 如果允许则返回true；否则返回false。
     */
    boolean isUploadEnable(String key, String contentType, String name);

    /**
     * 获取文件保存路径。
     *
     * @param key         上传文件key。
     * @param contentType 文件类型。
     * @param name        文件名。
     * @return 文件保存路径。
     */
    default String getPath(String key, String contentType, String name) {
        return "";
    }

    /**
     * 获取图片大小。
     * 当上传文件为图片时，并且返回的图片大小（长、高）大于0时，自动将图片修改为长宽不超过设置值的图片，并进行压缩。
     *
     * @param key 上传文件key。
     * @return 图片大小[长, 高]，如果返回空或0集则表示不需要调整图片。
     */
    default int[] getImageSize(String key) {
        return null;
    }

    /**
     * 获取存储处理器。
     *
     * @return 存储处理器。
     */
    default String getStorage() {
        return null;
    }

    /**
     * 处理上传信息。
     *
     * @param key  上传文件key。
     * @param name 文件名。
     * @param size 文件大小。
     * @param uri  文件URI地址。
     * @return 输出结果。
     */
    String upload(String key, String name, String size, String uri);
}
