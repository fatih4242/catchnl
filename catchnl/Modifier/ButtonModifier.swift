//
//  ButtonModifier.swift
//  catchnl
//
//  Created by Fatih Toker on 13.09.2023.
//

import Foundation
import SwiftUI

struct ButtonModifier: ViewModifier{
    func body(content: Content) -> some View {
        content
            .padding()
            .frame(maxWidth: .infinity)
            .foregroundColor(.white)
            
            .background(Color("primary-color"))
            .cornerRadius(8)
            .padding(.horizontal)
            

    }
}
