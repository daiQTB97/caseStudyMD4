class App {
    static DOMAIN = location.origin;

    static BASER_URL = this.DOMAIN + "/api/";

    static BASE_URL_USER = this.DOMAIN + "/api/users";

    static BASE_URL_CATEGORY = this.DOMAIN + "/api/categoryRest";

    static BASE_URL_PRODUCT = this.DOMAIN + "/api/products";

    static BASE_URL_NEW_USER = this.DOMAIN + "/api/users/create";

    static SweetAlert = class {
        static showSuccessAlert(t) {
            Swal.fire({
                icon: 'success',
                title: t,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1500
            })
        }

        static showErrorAlert(t) {
            Swal.fire({
                icon: 'error',
                title: 'Warning',
                text: t,
            })
        }

        static showConfirmDelete() {
            return Swal.fire({
                title: 'Are you sure to delete this property?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, delete it!'
            })
        }
    }

    static IziToast = class  {
        static showErrorAlert(m) {
            iziToast.error({
                title: 'Error',
                message: m,
            });
        }

        static showSuccessAlert(m) {
            iziToast.success({
                title: 'Success',
                position: 'topRight',
                message: m,
            });
        }
    }

    static formatNumber() {
        $(".num-space").number(true, 0, ',', ' ');
        $(".num-point").number(true, 0, ',', '.');
        $(".num-comma").number(true, 0, ',', ',');
    }

    static formatNumberSpace(x) {
        if (x == null) {
            return x;
        }
        return x.toString().replace(/ /g, "").replace(/\B(?=(\d{3})+(?!\d))/g, " ");
    }
}

class LocationRegion {
    constructor(id, provinceId, provinceName, districtId, districtName, wardId, wardName, address) {
        this.id = id;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.address = address;
    }
}

class Role {
    constructor(id = 1, name = "USER") {
        this.id = id;
        this.name = name;
    }
}

class User {
    constructor(id, fullName, username, password, phone,role, locationRegion) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.locationRegion = locationRegion;
    }
}

class Category{
    constructor(id, title,code) {
        this.id = id;
        this.title = title;
        this.code = code;
    }
}

class Product{
    constructor(id, title, category, quantity, price, image, deleted){
        this.id = id;
        this.title = title;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
        this.image = image;
        this.deleted = deleted;

    }
}



