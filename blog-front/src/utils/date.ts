import moment from 'moment'

export function formatStr(str: string, oldFormat: string, newFormat: string): string {
    return moment(str, oldFormat).format(newFormat)
}

export function formatStandStr(str: string, newFormat: string): string {
    if (str && newFormat) {
        return formatStr(str, "YYYY-MM-DD HH:mm:ss", newFormat);
    }
    return ""
}