import os

def count_lines_in_files(root_dir='.'):
    """
    Подсчитывает количество пустых, непустых и общее количество строк
    во всех файлах (текстовых) в указанной директории и её поддиректориях.
    Возвращает кортеж (пустые, непустые, всего).
    """
    empty_lines = 0
    non_empty_lines = 0
    total_lines = 0

    for dirpath, _, filenames in os.walk(root_dir):
        for filename in filenames:
            filepath = os.path.join(dirpath, filename)
            try:
                # Открываем файл в текстовом режиме с игнорированием ошибок кодировки
                with open(filepath, 'r', encoding='utf-8', errors='ignore') as f:
                    for line in f:
                        total_lines += 1
                        if line.strip() == '':
                            empty_lines += 1
                        else:
                            non_empty_lines += 1
            except (OSError, UnicodeDecodeError, PermissionError):
                # Пропускаем файлы, которые не удаётся прочитать как текст
                continue

    return empty_lines, non_empty_lines, total_lines


if __name__ == '__main__':
    empty, non_empty, total = count_lines_in_files()
    print(f"Пустых строк: {empty}")
    print(f"Непустых строк: {non_empty}")
    print(f"Всего: {total}")
    # Дополнительная проверка
    # print(f"Сумма пустых + непустых = {empty + non_empty} (должно равняться {total})")
