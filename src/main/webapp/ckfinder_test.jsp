<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>CKEditor & CKFinder Integration</title>
        <!-- Use CDN for CKEditor 4.22.1 (Last Free Version) to avoid License Key errors -->
        <script src="https://cdn.ckeditor.com/4.22.1/full/ckeditor.js"></script>
        <script src="${pageContext.request.contextPath}/static/ckfinder/ckfinder.js"></script>
    </head>

    <body>

        <h2>Trình soạn thảo bài viết</h2>
        <textarea id="myEditor" name="content"></textarea>

        <script>
            // Debug check
            if (typeof CKEDITOR === 'undefined') {
                document.write('<p style="color:red">Error: CKEditor library not found. Please check paths.</p>');
            }
            if (typeof CKFinder === 'undefined') {
                document.write('<p style="color:red">Error: CKFinder library not found. Please check paths.</p>');
            }

            // Cấu hình context path động
            var contextPath = '${pageContext.request.contextPath}';

            // Khởi tạo CKEditor
            if (typeof CKEDITOR !== 'undefined') {
                var editor = CKEDITOR.replace('myEditor', {
                    height: 400,
                    // Disable annoying plugins and warnings
                    removePlugins: 'exportpdf',
                    versionCheck: false,
                    // Cấu hình đường dẫn                // Use custom UploadController (bypassing CKFinder JAR)
                    uploadUrl: contextPath + '/api/upload',
                    filebrowserUploadUrl: contextPath + '/api/upload',
                    filebrowserImageUploadUrl: contextPath + '/api/upload',

                    // Keep CKFinder for browsing (might not work without JAR)
                    filebrowserBrowseUrl: contextPath + '/static/ckfinder/ckfinder.html',
                    filebrowserImageBrowseUrl: contextPath + '/static/ckfinder/ckfinder.html?type=Images',
                });

                // Kết nối CKFinder vào Editor instance
                if (typeof CKFinder !== 'undefined') {
                    CKFinder.setupCKEditor(editor);
                }
            }
        </script>
    </body>

    </html>