export type Page = {
    document: Document
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
    contentDirectory: File
}

type File = {
    name: string
    children: Array<File | string>
}

type Repository = {
    owner: string
    name: string
}
