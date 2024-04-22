export const getCurrentDateDetails = () => {
    const daysOfWeek = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
    const monthsOfYear = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'];

    const currentDate = new Date();
    const dayOfWeek = daysOfWeek[currentDate.getDay()];
    const month = monthsOfYear[currentDate.getMonth()];
    const dayOfMonth = currentDate.getDate();

    return `${month}${dayOfMonth}日，${dayOfWeek}。`;
}
export const getCurrentTimePeriod = (): string => {
    const currentDate = new Date();
    const currentHour = currentDate.getHours();

    if (currentHour >= 0 && currentHour < 12) {
        return '上午';
    } else if (currentHour >= 12 && currentHour < 18) {
        return '下午';
    } else {
        return '晚上';
    }
}
export const getCurrentDate = () => {
    const monthsOfYear = ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'];
    const currentDate = new Date();
    const year=currentDate.getFullYear()
    const month = monthsOfYear[currentDate.getMonth()];
    const dayOfMonth = currentDate.getDate();
    return `${year}年${month}${dayOfMonth}日`;
}
export const getCurrentDateForMat = () => {
    const monthsOfYear = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];
    const currentDate = new Date();
    const year=currentDate.getFullYear()
    const month = monthsOfYear[currentDate.getMonth()];
    const dayOfMonth = currentDate.getDate();
    return `${year}-${month}-${dayOfMonth}`;
}