<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ taglib prefix="c" uri="jakarta.tags.core" %>
    <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
      <!DOCTYPE html>
      <html lang="vi">

      <head>
        <meta charset="UTF-8">
        <title>Đặt lịch chăm sóc sức khỏe - HKH</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Booking/Booking.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/footer/footer.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/style/header/header.css">
      </head>

      <body>
        <jsp:include page="/style/header/header.jsp" />

        <main class="container">
          <!-- Left: Booking Form -->
          <section class="booking-section">
            <h3 class="section-title">
              🩺 Đặt lịch chăm sóc sức khỏe
            </h3>

            <form id="bookingForm">
              <div class="form-group">
                <label>Loại dịch vụ chăm sóc *</label>
                <select name="serviceType" id="serviceType" required>
                  <option value="">-- Chọn dịch vụ --</option>
                  <option value="Khám sức khỏe tổng quát">Khám sức khỏe tổng quát</option>
                  <option value="Chăm sóc sức khỏe tại nhà">Chăm sóc sức khỏe tại nhà</option>
                  <option value="Vật lý trị liệu / Phục hồi chức năng">Vật lý trị liệu / Phục hồi chức năng</option>
                  <option value="Chăm sóc sức khỏe người cao tuổi">Chăm sóc sức khỏe người cao tuổi</option>
                  <option value="Tư vấn sức khỏe online">Tư vấn sức khỏe online</option>
                </select>
              </div>

              <div class="form-row">
                <div class="form-group">
                  <label>Ngày hẹn *</label>
                  <input type="date" name="appointmentDate" id="appointmentDate" required>
                </div>
                <div class="form-group">
                  <label>Giờ hẹn *</label>
                  <input type="time" name="appointmentTime" id="appointmentTime" required>
                </div>
              </div>

              <div class="form-group">
                <label>Hình thức chăm sóc *</label>
                <div class="radio-group">
                  <label><input type="radio" name="careType" value="AtHome" checked> Tại nhà</label>
                  <label><input type="radio" name="careType" value="AtClinic"> Tại cơ sở META</label>
                </div>
              </div>

              <div class="form-group" id="addressGroup">
                <label>Địa chỉ chăm sóc *</label>
                <input type="text" name="address" id="address"
                  placeholder="Số nhà, tên đường, phường/xã, quận/huyện, tỉnh/thành">
              </div>

              <div class="form-group">
                <label>Ghi chú thêm cho META</label>
                <textarea name="notes" id="notes"
                  placeholder="Ví dụ: có bệnh nền, dị ứng thuốc, yêu cầu đặc biệt..."></textarea>
              </div>

              <button type="submit" class="btn-submit">
                <i class="fa fa-calendar-check"></i>
                Xác nhận đặt lịch
              </button>
            </form>
          </section>

          <!-- Right: Summary & Support Info -->
          <aside class="schedule-section">
            <h3 class="section-title">📋 Thông tin lịch hẹn</h3>

            <div class="summary-card" id="summaryCard">
              <p><strong>Dịch vụ:</strong> <span id="previewService">.....................</span></p>
              <p><strong>Thời gian:</strong> <span id="previewDateTime">.................. / ............</span></p>
              <p><strong>Hình thức:</strong> <span id="previewCareType">Tại nhà</span></p>
              <p><strong>Địa chỉ:</strong> <span id="previewAddress">.....................</span></p>
            </div>

            <div class="info-box">
              <h4>🏥 Lưu ý & Hỗ trợ</h4>
              <ul>
                <li>Đội ngũ chăm sóc sức khỏe được đào tạo bài bản.</li>
                <li>META sẽ liên hệ để xác nhận lịch hẹn sau khi tiếp nhận thông tin.</li>
                <li>Thời gian phục vụ: 8h00 - 21h00 (tất cả các ngày trong tuần).</li>
              </ul>
            </div>

            <div class="contact-box">
              <p>📞 Hotline hỗ trợ: <strong>1900 1234</strong></p>
              <p>✉ Email: <a href="mailto:csyt@meta.vn">csyt@meta.vn</a></p>
            </div>
          </aside>

        </main>

        <script src="${pageContext.request.contextPath}/style/header/header.js"></script>
        <script>
          document.addEventListener('DOMContentLoaded', () => {
            const dateInput = document.getElementById('appointmentDate');
            const today = new Date().toISOString().split('T')[0];
            dateInput.setAttribute('min', today);

            const careTypeRadios = document.querySelectorAll('input[name="careType"]');
            const addressGroup = document.getElementById('addressGroup');
            const addressInput = document.getElementById('address');

            careTypeRadios.forEach(radio => {
              radio.addEventListener('change', function () {
                if (this.value === 'AtHome') {
                  addressGroup.style.display = 'block';
                  addressInput.setAttribute('required', 'required');
                } else {
                  addressGroup.style.display = 'none';
                  addressInput.removeAttribute('required');
                  addressInput.value = '';
                }
                updatePreview();
              });
            });

            const serviceType = document.getElementById('serviceType');
            const appointmentDate = document.getElementById('appointmentDate');
            const appointmentTime = document.getElementById('appointmentTime');
            const address = document.getElementById('address');

            serviceType.addEventListener('change', updatePreview);
            appointmentDate.addEventListener('change', updatePreview);
            appointmentTime.addEventListener('change', updatePreview);
            address.addEventListener('input', updatePreview);
            careTypeRadios.forEach(r => r.addEventListener('change', updatePreview));

            function updatePreview() {
              const service = serviceType.value || '.....................';
              const date = appointmentDate.value || '..........';
              const time = appointmentTime.value || '.....';
              const careType = document.querySelector('input[name="careType"]:checked').value;
              const addr = address.value || '.....................';

              document.getElementById('previewService').textContent = service;
              document.getElementById('previewDateTime').textContent = date + ' / ' + time;
              document.getElementById('previewCareType').textContent = careType === 'AtHome' ? 'Tại nhà' : 'Tại cơ sở META';
              document.getElementById('previewAddress').textContent = careType === 'AtHome' ? addr : 'N/A';
            }

            // Form submission with improved error handling
            const form = document.getElementById('bookingForm');
            form.addEventListener('submit', function (e) {
              e.preventDefault();

              const formData = new FormData(form);
              const data = new URLSearchParams(formData);

              console.log('Submitting form data:', data.toString());

              fetch('${pageContext.request.contextPath}/booking', {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: data.toString()
              })
                .then(response => {
                  console.log('Response status:', response.status);
                  console.log('Response ok:', response.ok);
                  if (!response.ok) {
                    throw new Error('HTTP ' + response.status + ': ' + response.statusText);
                  }
                  return response.text();
                })
                .then(text => {
                  console.log('Response text:', text);
                  const result = JSON.parse(text);
                  if (result.status === 'success') {
                    alert('✅ ' + result.message);
                    form.reset();
                    updatePreview();
                  } else {
                    alert('❌ ' + result.message);
                  }
                })
                .catch(error => {
                  console.error('Error details:', error);
                  alert('❌ Lỗi: ' + error.message + '\nVui lòng kiểm tra console (F12) để xem chi tiết.');
                });
            });
          });
        </script>

      </body>

      </html>