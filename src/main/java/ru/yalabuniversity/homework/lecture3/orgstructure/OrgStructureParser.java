package ru.yalabuniversity.homework.lecture3.orgstructure;

import java.io.File;
import java.io.IOException;

public interface OrgStructureParser {
    Employee parseStructure(File csvFile) throws IOException;
}
