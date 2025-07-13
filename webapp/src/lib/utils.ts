import { type ClassValue, clsx } from 'clsx'
import { twMerge } from 'tailwind-merge'

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

export async function content(user: string, repo: string, path: string = "") {// todo add input validation for search
    return await fetch(`https://https://raw.githubusercontent.com/${user}/${repo}/HEAD/${path}`)
}
