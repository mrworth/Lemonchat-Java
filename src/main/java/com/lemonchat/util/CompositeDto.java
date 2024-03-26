package com.lemonchat.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CompositeDto<T1,T2> {
	T1 data;
	T2 metadata;
	
}
