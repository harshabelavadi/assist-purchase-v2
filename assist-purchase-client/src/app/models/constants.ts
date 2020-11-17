export const PROD_DETAILS_DISP_VALUES = {
    pname: 'Product Name', touchscreen: 'Touchscreen Feature', size: 'Screen Size (inches)', category: 'Category', transportMonitor: 'Transport Feature',
    description: 'Description', true: 'Yes', false: 'No', intellivue: 'Intellivue', effica: 'Effica', goldway: 'Goldway'};
export const PROD_DETAILS_ERRORMSG = 'Please enter both email address and contact number';
export const EMPTY = '';
export const ID = 'id';
export const DEFAULT = 'default';
export const PURCHASE_MESSAGE = (pid: string, name: string, email: string, mobile: string) => `Your order for purchase of item with Product-id: ${pid} is approved. You will be contacted by email or mobile by Philips personnel. Name: ${name}, Email: ${email}, Mobile: ${mobile}`;
export const ADD_PRODUCT_ERROR = 'Mandatory: Enter all fields';
export const DELETE_PRODUCT_ERROR = 'Select an option from dropdown to delete';
export const YES = 'Yes';
export const NO = 'No';
export const PRODUCT_ADDED = 'The Product has been added to Philips purchase portal successfully!';
export const PRODUCT_REMOVED = 'The Product has been removed from Philips purchase portal successfully!';
export const LOGIN_ERROR = 'Invalid Credentials Or User is not admin';
export const EMPTYFIELDS_ERRORMSG = 'Please fill all the textboxes';
export const EMAIL_INVALID = 'Email address is invalid';
export const CONTACT_INVALID = 'Contact is invalid';
export const EMAIL_VALIDATION = '^[a-zA-Z]{1}[a-zA-Z0-9.\-_]*@[a-zA-Z]{1}[a-zA-Z.-]*[a-zA-Z]{1}[.][a-zA-Z]{2,}$';
export const CONTACT_VALIDATION = '^[0-9]{10}$';

export enum PROD_DETAILS_KEYS {
    SIZE = 'size',
    PRODUCT_NAME = 'pname',
    DESCRIPTION = 'description',
    PID = 'pid',
    IMAGENAME = 'imgname',
    STAFF = 'staff'
}

export enum NAVIGATION_CONSTS {
    FROM_PRODUCT_DETAILS_TO_PURCHASE = '../../purchased-view',
    DASHBOARD = 'dashboard',
    FROM_ADDREMOVE_TO_REMCONFIRM = '../remove-confirmation',
    FROM_ADDREMOVE_TO_RESVIEW = '../results-view',
    FROM_REMOVECONFIRM_TO_RESVIEW = '../../results-view',
    FROM_LOGIN_TO_ADDREMOVE = '../add-remove-products'
}

export enum HIGHCHARTS_CONSTS {
    CHART = 'chart',
    LINE = 'line',
    DASH = 'dash',
    BENCHMARK = 'Benchmark',
    TITLE = 'Monitor Sales Statistics',
    SERIES_TITLE = 'Product Sale Stats'
}
