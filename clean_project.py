import os
import re

def remove_java_comments(text):
    def replacer(match):
        s = match.group(0)
        if s.startswith('/'):
            # C'est un commentaire
            return " " if s.startswith('/*') else ""
        else:
            # C'est une chaine de caracteres, on la garde
            return s

    pattern = re.compile(
        r'//.*?$|/\*.*?\*/|\'(?:\\.|[^\\\'])*\'|"(?:\\.|[^\\"])*"',
        re.DOTALL | re.MULTILINE
    )
    return re.sub(pattern, replacer, text)

def remove_xml_comments(text):
    # Enleve les commentaires XML
    return re.sub(r'', '', text, flags=re.DOTALL)

def clean_project(directory):
    for root, dirs, files in os.walk(directory):
        for file in files:
            file_path = os.path.join(root, file)
            
            # Fichiers Java
            if file.endswith(".java"):
                print(f"Nettoyage : {file_path}")
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        content = f.read()
                    
                    new_content = remove_java_comments(content)
                    
                    if new_content != content:
                        with open(file_path, 'w', encoding='utf-8') as f:
                            f.write(new_content)
                except Exception as e:
                    print(f"Erreur sur {file_path}: {e}")

            # Fichiers XML
            elif file.endswith(".xml"):
                print(f"Nettoyage XML : {file_path}")
                try:
                    with open(file_path, 'r', encoding='utf-8') as f:
                        content = f.read()
                    
                    new_content = remove_xml_comments(content)
                    
                    if new_content != content:
                        with open(file_path, 'w', encoding='utf-8') as f:
                            f.write(new_content)
                except Exception as e:
                    print(f"Erreur sur {file_path}: {e}")

if __name__ == "__main__":
    current_dir = os.getcwd()
    print("Demarrage du nettoyage...")
    clean_project(current_dir)
    print("Termine.")