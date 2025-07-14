import { type ClassValue, clsx } from 'clsx'
import { twMerge } from 'tailwind-merge'

export type Page = {
    site: Document
    content: string
}

type Document = {
    title: string
    icon: string
    favicon: string
    contentRoot: string
    contentGroups: Map<String, String>
    repo: Repository
    homePath: string
    contentDirectory: Directory
}

type Directory = {
    name: string
    children: Array<Directory | string>
}

type Repository = {
    user: string
    name: string
}

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

export async function content(user: string, repo: string, path: string = "") {// todo add input validation for search
    return await fetch(`https://https://raw.githubusercontent.com/${user}/${repo}/HEAD/${path}`)
}
