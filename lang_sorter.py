from pathlib import Path


# Имя .lang-файла в текущей директории
FILE_NAME = "ru_ru.lang"


def sort_lang_file(file_name: str) -> None:
    path = Path.cwd() / file_name

    if not path.exists():
        print(f"Файл не найден: {path}")
        return

    lines = path.read_text(encoding="utf-8").splitlines(keepends=True)

    entries = []
    other_lines = []

    for line in lines:
        stripped = line.strip()

        # Пустые строки и комментарии оставляем в конце
        if not stripped or stripped.startswith("#"):
            other_lines.append(line)
            continue

        # Разделяем строку только по первому знаку "="
        if "=" in line:
            key, value = line.split("=", 1)
            entries.append((key.strip().lower(), line))
        else:
            other_lines.append(line)

    # Сортировка строк по ключу слева от "="
    entries.sort(key=lambda item: item[0])

    sorted_lines = [line for _, line in entries] + other_lines

    # Записываем результат во временный файл,
    # чтобы не повредить исходный при ошибке
    temp_path = path.with_suffix(path.suffix + ".tmp")
    temp_path.write_text("".join(sorted_lines), encoding="utf-8")
    temp_path.replace(path)

    print(f"Файл отсортирован: {path}")


if __name__ == "__main__":
    sort_lang_file(FILE_NAME)
