// function addNewSmartPhone() {
//     let producer = $('#producer').val();
//     let model = $('#model').val();
//     let price = $('#price').val();
//     let newSmartPhone = {
//         producer: producer,
//         model: model,
//         price: price
//     };
//
//     $.ajax({
//         headers: {
//             'Accept': 'application/json',
//             'Content-Type': 'application/json'
//         },
//         type: 'POST',
//         data: JSON.stringify(newSmartPhone),
//         url: "http://localhost:8080/api/smartphone",
//         success: successHandler
//     });
//
//     event.preventDefault();
// }

function successHandler() {
    $.ajax({
        type: 'GET',
        url: "http://localhost:8080/api/smartphone",
        success: function (data) {
            let content = '<table id="display-list" border="1"> <tr>' +
                '<th>Producer</th>' +
                '<th>Model</th>' +
                '<th>Price</th>' +
                '<th>Delete</th>' +
                '</tr>';
            for (let i = 0; i < data.length; i++) {
                content += getSmartPhone(data[i]);
            }
            content += '</table>';
            document.getElementById('smartphoneList').innerHTML = content;
            document.getElementById('smartphoneList').style.display = "block";
            document.getElementById('add-smartphone').style.display = "none";
            document.getElementById('title').style.display = "block";

            document.getElementById('display').style.display = "none";
            document.getElementById('display-create').style.display = "inline-block";
        }
    })
}

function displayFormCreate() {
    document.getElementById('smartphoneList').style.display = "none";
    document.getElementById('add-smartphone').style.display = "block";
    document.getElementById('display-create').style.display = "none";
    document.getElementById('title').style.display = "none";
}

// function getSmartPhone(smartPhone) {
//     return  `<tr><td >${smartPhone.producer}</td><td >${smartPhone.model}</td><td >${smartPhone.price}</td>` +
//         `<td class="btn"><button class="deleteSmartphone" onclick="deleteSmartphone(${smartPhone.id})">Delete</button></td></tr>`;
// }

function deleteSmartphone(id) {
    $.ajax({
        type: "DELETE",
        url: `http://localhost:8080/api/smartphone/${id}`,
        success: successHandler
    });
}

function getSmartPhone(smartPhone) {
    return  `<tr>
                <td>${smartPhone.producer}</td>
                <td>${smartPhone.model}</td>
                <td>${smartPhone.price}</td>
                <td class="btn">
                    <button class="deleteSmartphone" onclick="deleteSmartphone(${smartPhone.id})">Delete</button>
                    <button class="editSmartphone" onclick="showEditForm(${smartPhone.id})">Edit</button>
                </td>
            </tr>`;
}

function showEditForm(id) {
    $.ajax({
        type: 'GET',
        url: `http://localhost:8080/api/smartphone`,
        success: function (data) {
            let phone = data.find(p => p.id === id);
            if (!phone) return alert("Smartphone not found!");

            $('#producer').val(phone.producer);
            $('#model').val(phone.model);
            $('#price').val(phone.price);

            // Ghi lại id đang chỉnh sửa
            $('#add-smartphone').attr('data-edit-id', id);

            document.getElementById('smartphoneList').style.display = "none";
            document.getElementById('add-smartphone').style.display = "block";
            document.getElementById('display-create').style.display = "none";
            document.getElementById('title').style.display = "none";

            // Thay nút Add bằng nút Update
            $('#add-smartphone input[type="submit"]').val('Update');
        }
    });
}

function addNewSmartPhone() {
    event.preventDefault();

    let producer = $('#producer').val();
    let model = $('#model').val();
    let price = $('#price').val();

    let newSmartPhone = { producer, model, price };

    // Kiểm tra xem có đang ở chế độ "chỉnh sửa" không
    let editId = $('#add-smartphone').attr('data-edit-id');

    if (editId) {
        // ---- UPDATE ----
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: 'PUT',
            data: JSON.stringify(newSmartPhone),
            url: `http://localhost:8080/api/smartphone/${editId}`,
            success: function() {
                successHandler();
                resetForm();
            }
        });
    } else {
        // ---- CREATE ----
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: 'POST',
            data: JSON.stringify(newSmartPhone),
            url: "http://localhost:8080/api/smartphone",
            success: function() {
                successHandler();
                resetForm();
            }
        });
    }
}

function resetForm() {
    $('#producer').val('');
    $('#model').val('');
    $('#price').val('');
    $('#add-smartphone').removeAttr('data-edit-id');
    $('#add-smartphone input[type="submit"]').val('Add');
}
