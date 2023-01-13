package ar.vga.com.mapper;

import lombok.Data;

public interface StatementSource {
    String getSql();
    Object [] getParameters();
}
