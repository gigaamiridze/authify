export function getAvatarLetter(name?: string): string {
  if (!name) {
    return 'U';
  }

  return name.trim().charAt(0).toUpperCase();
}

export function isEmpty(value: string | null): boolean {
  return value === null || value.length === 0;
}

export function isBlank(value: string | null): boolean {
  return value === null || value.trim().length === 0;
}