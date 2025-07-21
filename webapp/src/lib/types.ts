export type Page = {
    readonly document: Document
    readonly content: string
}

export type Pulse = {
    readonly mid: string,
    readonly faint: string
}

type Document = {
    readonly title: string
    readonly icon: string
    readonly favicon: string
    readonly contentRoot: string
    readonly contentGroups: Map<String, String>
    readonly repo: Repository
    readonly homePath: string
    readonly contentDirectory: File
}

type File = {
    readonly name: string
    readonly children: Array<File | string>
}

type Repository = {
    readonly owner: string
    readonly name: string
}
